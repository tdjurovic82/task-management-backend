package com.example.workforcemanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

   @Id
   private Long uprn;
   private String address;
   private String postcode;
   private Double latitude;
   private Double longitude;



}
