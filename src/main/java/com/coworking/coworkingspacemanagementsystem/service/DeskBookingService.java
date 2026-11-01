package com.coworking.coworkingspacemanagementsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.coworking.coworkingspacemanagementsystem.entity.DeskBooking;
import com.coworking.coworkingspacemanagementsystem.entity.DeskBookingStatus;
import com.coworking.coworkingspacemanagementsystem.repository.DeskBookingRepository;
import com.coworking.coworkingspacemanagementsystem.repository.UsageChargeRepository;

@Service
public class DeskBookingService {

    private final DeskBookingRepository deskBookingRepository;
    private final UsageChargeRepository usageChargeRepository;

    public DeskBookingService(
        DeskBookingRepository deskBookingRepository,
        UsageChargeRepository usageChargeRepository) {
            this.deskBookingRepository = deskBookingRepository;
            this.usageChargeRepository = usageChargeRepository;
        }

    public String saveDeskBooking(DeskBooking deskBooking) {

    if (deskBooking.getBookingDate().isBefore(LocalDate.now())) {
        return "Desk booking date cannot be in the past";
    }

    boolean alreadyBooked = deskBookingRepository
            .existsByHostAndBookingDateAndStatusNot(
                    deskBooking.getHost(),
                    deskBooking.getBookingDate(),
                    DeskBookingStatus.WITHDRAWN
            );

    if (alreadyBooked) {
        return "Host already has a desk booking on that date";
    }

    deskBooking.setStatus(DeskBookingStatus.RESERVED);

    deskBookingRepository.save(deskBooking);

    return "Desk booking saved successfully";
}

    public List<DeskBooking> getBookingsBetween(LocalDate start, LocalDate end) {
        return deskBookingRepository.findByBookingDateBetweenOrderByBookingDateAsc(start, end);
    }

    public List<DeskBooking> getAllDeskBookings() {
        return deskBookingRepository.findAll();
    }

    public List<DeskBooking> getBookingsByStatus(DeskBookingStatus status) {
        return deskBookingRepository.findByStatusOrderByBookingDateAsc(status);
    }

    public Optional<DeskBooking> getDeskBookingById(UUID id) {
        return deskBookingRepository.findById(id);
    }

    public List<Object[]> getBookingStatsByStatus() {
    return deskBookingRepository.countBookingsByStatus();
}

    public Optional<DeskBooking> updateDeskBooking(UUID id, DeskBooking deskBooking) {

    Optional<DeskBooking> optionalBooking = deskBookingRepository.findById(id);

    if (optionalBooking.isPresent()) {
        deskBooking.setId(id);
        deskBooking.setStatus(optionalBooking.get().getStatus());

        return Optional.of(deskBookingRepository.save(deskBooking));
    }

    return Optional.empty();

    }

    public String deleteDeskBooking(UUID id) {

    Optional<DeskBooking> optionalBooking = deskBookingRepository.findById(id);

    if (optionalBooking.isEmpty()) {
        return "The desk booking with that id does not exist";
    }

    if (usageChargeRepository.existsByDeskBooking(optionalBooking.get())) {
        return "That desk booking has usage charges, delete them first";
    }

    deskBookingRepository.delete(optionalBooking.get());

    return "Desk booking deleted successfully";
}
    public String updateBookingStatus(UUID id, DeskBookingStatus newStatus) {

    Optional<DeskBooking> optionalBooking = deskBookingRepository.findById(id);

    if (optionalBooking.isEmpty()) {
        return "The desk booking with that id does not exist";
    }

    DeskBooking booking = optionalBooking.get();

    DeskBookingStatus currentStatus = booking.getStatus();

    boolean allowed = false;

    if (currentStatus == DeskBookingStatus.RESERVED) {
        if (newStatus == DeskBookingStatus.IN_USE ||
                newStatus == DeskBookingStatus.WITHDRAWN) {
            allowed = true;
        }
    } else if (currentStatus == DeskBookingStatus.IN_USE) {
        if (newStatus == DeskBookingStatus.ENDED ||
                newStatus == DeskBookingStatus.WITHDRAWN) {
            allowed = true;
        }
    }

    if (!allowed) {
        return "Cannot change status from " + currentStatus + " to " + newStatus;
    }

    booking.setStatus(newStatus);
    deskBookingRepository.save(booking);

    return "Desk booking status updated successfully";
}

public String cancelBookingsForHost(UUID hostId, LocalDate date) {

    int cancelled = deskBookingRepository.cancelBookingsForHost(
            hostId,
            date,
            DeskBookingStatus.WITHDRAWN,
            DeskBookingStatus.ENDED
    );

    return cancelled + " desk bookings cancelled";
}
    
}