package com.coworking.coworkingspacemanagementsystem.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coworking.coworkingspacemanagementsystem.entity.Host;

public interface HostRepository extends JpaRepository<Host, UUID> {
}