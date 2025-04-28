package com.example.workforcemanagement.apikey;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table


public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String apiKeyValue;

    private boolean valid;

    public ApiKey() {
    }

    public ApiKey(String apiKeyValue) {
    this.apiKeyValue = apiKeyValue;
    this.valid = true;
}

}
