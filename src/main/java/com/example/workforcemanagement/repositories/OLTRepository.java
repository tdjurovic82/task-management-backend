package com.example.workforcemanagement.repositories;

import com.example.workforcemanagement.entities.OLT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface OLTRepository extends JpaRepository<OLT, Long > {
    Optional<OLT> findByOltName(String oltName);



    @Query("SELECT o FROM OLT o JOIN o.uprns u WHERE u = :uprn")
    Optional<OLT> findByUprn(@Param("uprn") Long uprn);
}
