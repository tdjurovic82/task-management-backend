package com.example.workforcemanagement.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LocationDTO {

    private Long uprn;
    private String address;
    private String postcode;
    private Double latitude;
    private Double longitude;
}
