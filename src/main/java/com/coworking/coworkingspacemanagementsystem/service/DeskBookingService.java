package com.coworking.coworkingspacemanagementsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.coworking.coworkingspacemanagementsystem.entity.DeskBooking;
import com.coworking.coworkingspacemanagementsystem.entity.DeskBookingStatus;
import com.coworking.coworkingspacemanagementsystem.repository.DeskBookingRepository;

@Service
public class DeskBookingService {

    private final DeskBookingRepository deskBookingRepository;

    public DeskBookingService(DeskBookingRepository deskBookingRepository) {
        this.deskBookingRepository = deskBookingRepository;
    }

    public String saveDeskBooking(DeskBooking deskBooking) {
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

    public Optional<DeskBooking> updateDeskBooking(UUID id, DeskBooking deskBooking) {
        if (deskBookingRepository.existsById(id)) {
            deskBooking.setId(id);
            return Optional.of(deskBookingRepository.save(deskBooking));
        }
        return Optional.empty();
    }

    public String deleteDeskBooking(UUID id) {
        Optional<DeskBooking> deskBooking = deskBookingRepository.findById(id);

        if (deskBooking.isPresent()) {
            deskBookingRepository.delete(deskBooking.get());
            return "Desk booking deleted successfully";
        }

        return "The desk booking with that id does not exist";
    }
}