package com.coworking.coworkingspacemanagementsystem.service;

import com.coworking.coworkingspacemanagementsystem.entity.DeskBooking;
import com.coworking.coworkingspacemanagementsystem.entity.DeskBookingStatus;
import com.coworking.coworkingspacemanagementsystem.entity.UsageCharge;
import com.coworking.coworkingspacemanagementsystem.repository.DeskBookingRepository;
import com.coworking.coworkingspacemanagementsystem.repository.UsageChargeRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsageChargeService {

    private final UsageChargeRepository usageChargeRepository;
    private final DeskBookingRepository deskBookingRepository;

    public UsageChargeService(
            UsageChargeRepository usageChargeRepository,
            DeskBookingRepository deskBookingRepository) {
        this.usageChargeRepository = usageChargeRepository;
        this.deskBookingRepository = deskBookingRepository;
    }

    public String saveUsageCharge(UsageCharge usageCharge, UUID deskBookingId) {

        Optional<DeskBooking> optionalBooking =
                deskBookingRepository.findById(deskBookingId);

        if (optionalBooking.isEmpty()) {
            return "The desk booking with that id does not exist";
        }

        DeskBooking booking = optionalBooking.get();

        if (booking.getStatus() != DeskBookingStatus.ENDED) {
            return "Usage charges are only for ENDED desk bookings";
        }

        usageCharge.setDeskBooking(booking);
        usageCharge.setChargedDate(LocalDate.now());

        usageChargeRepository.save(usageCharge);

        return "Usage charge saved successfully";
    }

    public List<UsageCharge> getChargesByMemberCode(String memberCode) {
        return usageChargeRepository.findChargesByMemberCode(memberCode);
    }

    public String deleteUsageCharge(UUID id) {

        Optional<UsageCharge> optionalCharge = usageChargeRepository.findById(id);

        if (optionalCharge.isEmpty()) {
            return "The usage charge with that id does not exist";
        }

        usageChargeRepository.delete(optionalCharge.get());

        return "Usage charge deleted successfully";
    }
}