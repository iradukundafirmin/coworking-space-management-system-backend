package com.coworking.coworkingspacemanagementsystem.repository;

import java.util.List;

import com.coworking.coworkingspacemanagementsystem.entity.DeskBooking;
import com.coworking.coworkingspacemanagementsystem.entity.UsageCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UsageChargeRepository extends JpaRepository<UsageCharge, UUID> {
    @Query("SELECT u FROM UsageCharge u " +
    "WHERE u.deskBooking.member.memberCode = :memberCode " +
    "ORDER BY u.chargedDate DESC")
       
    List<UsageCharge> findChargesByMemberCode(@Param("memberCode") String memberCode);

    boolean existsByDeskBooking(DeskBooking deskBooking);
}