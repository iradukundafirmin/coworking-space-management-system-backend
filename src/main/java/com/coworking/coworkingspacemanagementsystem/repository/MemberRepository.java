package com.coworking.coworkingspacemanagementsystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coworking.coworkingspacemanagementsystem.entity.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    List<Member> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
    
    @Query("SELECT DISTINCT b.member FROM DeskBooking b WHERE b.host.id = :hostId")
    List<Member> findMembersByHostId(@Param("hostId") UUID hostId);

}