package com.coworking.coworkingspacemanagementsystem.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.coworking.coworkingspacemanagementsystem.entity.DeskBookingStatus;
import com.coworking.coworkingspacemanagementsystem.repository.DeskBookingRepository;
import com.coworking.coworkingspacemanagementsystem.repository.HostRepository;
import com.coworking.coworkingspacemanagementsystem.repository.MemberRepository;
import com.coworking.coworkingspacemanagementsystem.repository.UsageChargeRepository;

@Service
public class DashboardService {

    private final MemberRepository memberRepository;
    private final HostRepository hostRepository;
    private final DeskBookingRepository deskBookingRepository;
    private final UsageChargeRepository usageChargeRepository;
    private final FloorService floorService;

    public DashboardService(
            MemberRepository memberRepository,
            HostRepository hostRepository,
            DeskBookingRepository deskBookingRepository,
            UsageChargeRepository usageChargeRepository,
            FloorService floorService) {

        this.memberRepository = memberRepository;
        this.hostRepository = hostRepository;
        this.deskBookingRepository = deskBookingRepository;
        this.usageChargeRepository = usageChargeRepository;
        this.floorService = floorService;
    }

    public Map<String, Long> getDashboardSummary() {

        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("members", memberRepository.count());

        summary.put("hosts", hostRepository.count());

        summary.put("freeFloors", (long) floorService.getFreeFloors().size());

        summary.put("bookingsToday",
                deskBookingRepository.countByBookingDate(LocalDate.now()));

        summary.put("reserved",
                deskBookingRepository.countByStatus(DeskBookingStatus.RESERVED));

        summary.put("usageCharges", usageChargeRepository.count());

        return summary;
    }
}