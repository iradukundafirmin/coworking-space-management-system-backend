package com.coworking.coworkingspacemanagementsystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coworking.coworkingspacemanagementsystem.entity.Facility;

public interface FacilityRepository extends JpaRepository<Facility, UUID> {

    @Query("SELECT f FROM Facility f WHERE f.hosts IS EMPTY")
    List<Facility> findUnusedFacilities();
}