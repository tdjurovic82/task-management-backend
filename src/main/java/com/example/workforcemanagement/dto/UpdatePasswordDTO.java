package com.example.workforcemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordDTO {

    @NotBlank(message = "Password is required")
        private String password;
    }

