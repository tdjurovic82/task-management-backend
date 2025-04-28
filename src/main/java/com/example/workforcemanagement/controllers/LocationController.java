package com.example.workforcemanagement.controllers;

import com.example.workforcemanagement.dto.LocationDTO;
import com.example.workforcemanagement.services.LocationService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/locations")
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@RequestBody @Valid LocationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.createLocation(dto));
    }

    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @DeleteMapping("/{uprn}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long uprn) {
        locationService.deleteLocation(uprn);
        return ResponseEntity.ok("Location with uprn" + uprn + " deleted");
    }

    @GetMapping("/by-uprn/{uprn}")
    public ResponseEntity<LocationDTO> getByUprn(@PathVariable Long uprn) {
        LocationDTO loc = locationService.findByUprnDTO(uprn);
        return ResponseEntity.ok(loc);
    }

    @GetMapping("/by-uprn")
    public ResponseEntity<List<LocationDTO>> getByListOfUprns(@RequestParam Long[] uprns) {
        List<LocationDTO> locations = locationService.findByUprnsDTO(uprns);

        return ResponseEntity.ok(locations);
    }

    @GetMapping("/by-address")
    public ResponseEntity<LocationDTO> getByAddress(@RequestParam String address) {
        return ResponseEntity.ok(locationService.findByAddressDTO(address));
    }

    @GetMapping("/by-postcode")
    public ResponseEntity<List<LocationDTO>> getByPostcode(@RequestParam String postcode) {
        return ResponseEntity.ok(locationService.findByPostcodeDTO(postcode));
    }


    @GetMapping("/olts")
    public ResponseEntity<?> getOlts(@RequestParam(required = false) Long uprn) {
        if (uprn != null) {
            String olt = locationService.getOltNameByUprn(uprn);
            return ResponseEntity.ok(olt);
        } else {
            List<String> allOlts = locationService.getAllOltNames();
            return ResponseEntity.ok(allOlts);
        }
    }


}