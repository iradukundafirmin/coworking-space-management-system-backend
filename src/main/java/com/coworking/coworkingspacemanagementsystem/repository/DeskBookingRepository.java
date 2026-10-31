package com.coworking.coworkingspacemanagementsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coworking.coworkingspacemanagementsystem.entity.DeskBooking;

public interface DeskBookingRepository extends JpaRepository<DeskBooking, UUID> {
}