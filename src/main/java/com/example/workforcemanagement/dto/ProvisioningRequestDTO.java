package com.example.workforcemanagement.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProvisioningRequestDTO {
    @Pattern(regexp = "(?i)^ALCLF[A-Z0-9]{6,}$", message = "Invalid serial number format")
    private String serialNumber;

    private String oltName;

    @Pattern(regexp = "^1-1-\\d+-\\d+$", message = "Port must be in format 1-1-x-x.")
    private String port;

    private Long taskId;
    private String packageName;
}
