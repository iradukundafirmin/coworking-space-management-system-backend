package com.coworking.coworkingspacemanagementsystem.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coworking.coworkingspacemanagementsystem.entity.DeskBooking;
import com.coworking.coworkingspacemanagementsystem.entity.DeskBookingStatus;

public interface DeskBookingRepository extends JpaRepository<DeskBooking, UUID> {

    List<DeskBooking> findByStatusOrderByBookingDateAsc(DeskBookingStatus status);
    List<DeskBooking> findByBookingDateBetweenOrderByBookingDateAsc(
        LocalDate start, LocalDate end);

}