package com.coworking.coworkingspacemanagementsystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coworking.coworkingspacemanagementsystem.entity.Floor;
import com.coworking.coworkingspacemanagementsystem.entity.Host;

public interface HostRepository extends JpaRepository<Host, UUID> {
    @Query("SELECT h FROM Host h JOIN h.facilities f WHERE LOWER(f.name) = LOWER(:name)")
    List<Host> findHostsByFacilityName(@Param("name") String name);

    boolean existsByFloor(Floor floor);

}