package com.example.workforcemanagement.repositories;

import com.example.workforcemanagement.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByUprn(Long uprn);

    List<Location> findByUprnIn(List<Long> uprns);

    Location findByAddress(String address);

    List<Location> findByPostcode(String postcode);

}
