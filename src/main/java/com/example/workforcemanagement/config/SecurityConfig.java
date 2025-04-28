package com.example.workforcemanagement.config;

import com.example.workforcemanagement.apikey.ApiKey;
import com.example.workforcemanagement.repositories.ApiKeyRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Value("${security.api-key.header-name}")
    private String headerName;
    //@Value("${security.api-key.value}")
    //private String apiKeyValue;
    private final ApiKeyRepository apiKeyRepository;

    public SecurityConfig(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        ApiKeyAuthFilter filter = new ApiKeyAuthFilter(headerName);

//        filter.setAuthenticationManager(authentication -> {
//            String apiKey = (String) authentication.getPrincipal();
//
//
//            if (!apiKeyValue.equals(apiKey)) {
//                throw new BadCredentialsException("Invalid API key");
//            }
//
//            authentication.setAuthenticated(true);
//            return authentication;
//        });
        filter.setAuthenticationManager(authentication -> {
            String apiKey = (String) authentication.getPrincipal();

            ApiKey found = apiKeyRepository.findByApiKeyValue(apiKey);
            if (found == null) {
                throw new BadCredentialsException("Invalid API Key");
            }

            authentication.setAuthenticated(true);
            return authentication;
        });


        http    .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/v3/**",
                                "/v3/api-docs.yaml",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/swagger-ui/index.html",
                               // "/tasks",
                                "/generate-key",
                                "/h2-console/**")


                        .permitAll()
                       // .requestMatchers("/login", "/h2-console/**").permitAll()
                      .anyRequest().authenticated()
                )
                //.addFilter(filter);
                .addFilterBefore(filter, AbstractPreAuthenticatedProcessingFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, excep) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.setContentType("application/json");
                            res.getWriter().write("{ \"error\": \"" + excep.getMessage() + "\" }");
                        })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
