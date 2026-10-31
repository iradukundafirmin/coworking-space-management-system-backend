package com.coworking.coworkingspacemanagementsystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coworking.coworkingspacemanagementsystem.entity.Floor;

public interface FloorRepository extends JpaRepository<Floor, UUID> {

    @Query("SELECT f FROM Floor f WHERE NOT EXISTS " +
       "(SELECT h FROM Host h WHERE h.floor = f) " +
       "ORDER BY f.floorNumber ASC")
    List<Floor> findFreeFloors();
}
