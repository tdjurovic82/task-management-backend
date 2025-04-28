package com.example.workforcemanagement.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class ApiKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String headerName;


    public ApiKeyAuthFilter(String headerName) {
        this.headerName = headerName;
    }




    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {

           return request.getHeader(headerName);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }


}
