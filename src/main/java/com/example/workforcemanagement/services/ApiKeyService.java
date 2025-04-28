package com.example.workforcemanagement.services;

import com.example.workforcemanagement.repositories.ApiKeyRepository;
import com.example.workforcemanagement.apikey.ApiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ApiKeyService {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    public ApiKey createApiKey() {

        String value = UUID.randomUUID().toString();
        return apiKeyRepository.save(new ApiKey(value));
    }

    public List<ApiKey> getAllApiKeys() {

        return apiKeyRepository.findAll();

    }

}
