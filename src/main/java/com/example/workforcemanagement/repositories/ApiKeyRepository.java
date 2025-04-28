package com.example.workforcemanagement.repositories;

import com.example.workforcemanagement.apikey.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, String> {

    ApiKey findByApiKeyValue(String apiKeyValue);

}

