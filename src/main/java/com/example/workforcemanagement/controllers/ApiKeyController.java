package com.example.workforcemanagement.controllers;

import com.example.workforcemanagement.services.ApiKeyService;
import com.example.workforcemanagement.apikey.ApiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiKeyController {

    @Autowired
    public ApiKeyService apiKeyService;

    @GetMapping("/generate-key")
    public ApiKey generateKey() {
        return apiKeyService.createApiKey();
    }
    @GetMapping("/list-keys")
        public List<ApiKey> getKeys() {
            return apiKeyService.getAllApiKeys();
        }

}
