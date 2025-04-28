package com.example.workforcemanagement.apikey;

import java.util.UUID;

public class ApiKeyGenerator {
    public static void main(String[] args) {
        String apiKey = UUID.randomUUID().toString();
        System.out.println("New API key: " + apiKey);
    }
}
