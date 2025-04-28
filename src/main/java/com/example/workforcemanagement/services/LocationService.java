package com.example.workforcemanagement.services;

import com.example.workforcemanagement.dto.LocationDTO;
import com.example.workforcemanagement.entities.Location;
import com.example.workforcemanagement.entities.OLT;
import com.example.workforcemanagement.exceptions.LocationException;
import com.example.workforcemanagement.repositories.LocationRepository;
import com.example.workforcemanagement.repositories.OLTRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final OLTRepository oltRepository;

    public LocationService(LocationRepository locationRepository, OLTRepository oltRepository) {
        this.locationRepository = locationRepository;
        this.oltRepository = oltRepository;
    }

    public LocationDTO findByUprnDTO(Long uprn) {
        Location loc = locationRepository.findById(uprn)
                .orElseThrow(() -> new LocationException("Location not found"));
        return mapToResponse(loc);
    }

    public List<LocationDTO> findByUprnsDTO(Long uprns[]) {
        List<Location> locs = locationRepository.findByUprnIn(Arrays.asList(uprns));

        return locs.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public LocationDTO findByAddressDTO(String address) {
        Location location = locationRepository.findByAddress(address);
        if (location == null) throw new LocationException("Location not found");
        return mapToResponse(location);
    }

    public List<LocationDTO> findByPostcodeDTO(String postcode) {
        return locationRepository.findByPostcode(postcode).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public LocationDTO createLocation(LocationDTO dto) {
        if (locationRepository.existsById(dto.getUprn())) {
            throw new LocationException("Location with this UPRN already exists");
        }
        Location saved = locationRepository.save(mapToEntity(dto));
        return mapToResponse(saved);
    }

    public void deleteLocation(Long uprn) {
        if (!locationRepository.existsById(uprn)) {
            throw new LocationException("Location not found");
        }
        locationRepository.deleteById(uprn);
    }

    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    public String getOltNameByUprn(Long uprn) {
        return oltRepository.findByUprn(uprn)
                .orElseThrow(() -> new LocationException("There is no defined OLT for this UPRN"))
                .getOltName();
    }
    public List<String> getAllOltNames() {
        return oltRepository.findAll()
                .stream()
                .map(OLT::getOltName)
                .collect(Collectors.toList());
    }

    public LocationDTO mapToResponse(Location location) {
        return LocationDTO.builder()
                .uprn(location.getUprn())
                .address(location.getAddress())
                .postcode(location.getPostcode())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

    public Location mapToEntity(LocationDTO locationDTO) {
        return Location.builder()
                .uprn(locationDTO.getUprn())
                .address(locationDTO.getAddress())
                .postcode(locationDTO.getPostcode())
                .latitude(locationDTO.getLatitude())
                .longitude(locationDTO.getLongitude())
                .build();
    }

}
