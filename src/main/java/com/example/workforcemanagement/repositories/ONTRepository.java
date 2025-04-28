package com.example.workforcemanagement.repositories;

import com.example.workforcemanagement.entities.ONT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ONTRepository extends JpaRepository<ONT, Long> {
        boolean existsBySerialNumber(String serialNumber);

        boolean existsByUprn(Long uprn);
    }
