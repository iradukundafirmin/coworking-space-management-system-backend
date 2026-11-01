package com.coworking.coworkingspacemanagementsystem.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.coworking.coworkingspacemanagementsystem.entity.DeskBooking;
import com.coworking.coworkingspacemanagementsystem.entity.DeskBookingStatus;
import com.coworking.coworkingspacemanagementsystem.entity.Host;

public interface DeskBookingRepository extends JpaRepository<DeskBooking, UUID> {

    List<DeskBooking> findByStatusOrderByBookingDateAsc(DeskBookingStatus status);

    List<DeskBooking> findByBookingDateBetweenOrderByBookingDateAsc(
        LocalDate start, LocalDate end);

    long countByBookingDate(LocalDate bookingDate);
    long countByStatus(DeskBookingStatus status);

    boolean existsByHostAndBookingDateAndStatusNot(
        Host host,
        LocalDate bookingDate,
        DeskBookingStatus status);

    @Query("SELECT d.status, COUNT(d) FROM DeskBooking d GROUP BY d.status")
    List<Object[]> countBookingsByStatus();

    @Modifying
    @Transactional
    @Query("UPDATE DeskBooking d SET d.status = :newStatus " +
       "WHERE d.host.id = :hostId " +
       "AND d.bookingDate = :date " +
       "AND d.status <> :excludedStatus")
       
    int cancelBookingsForHost(
        @Param("hostId") UUID hostId,
        @Param("date") LocalDate date,
        @Param("newStatus") DeskBookingStatus newStatus,
        @Param("excludedStatus") DeskBookingStatus excludedStatus
);


}