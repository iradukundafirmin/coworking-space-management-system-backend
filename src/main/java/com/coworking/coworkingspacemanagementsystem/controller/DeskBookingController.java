package com.coworking.coworkingspacemanagementsystem.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.coworkingspacemanagementsystem.entity.DeskBooking;
import com.coworking.coworkingspacemanagementsystem.entity.DeskBookingStatus;
import com.coworking.coworkingspacemanagementsystem.service.DeskBookingService;


@RestController
@RequestMapping("/api/desk-bookings")
public class DeskBookingController {

    private final DeskBookingService deskBookingService;

    public DeskBookingController(DeskBookingService deskBookingService) {
        this.deskBookingService = deskBookingService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveDeskBooking(@RequestBody DeskBooking deskBooking) {
        String message = deskBookingService.saveDeskBooking(deskBooking);

        if (message.equals("Desk booking saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeskBooking>> getAllDeskBookings() {
        return new ResponseEntity<>(deskBookingService.getAllDeskBookings(), HttpStatus.OK);
    }

    @GetMapping("/{id:[0-9a-fA-F-]{36}}")
    public ResponseEntity<?> getDeskBookingById(@PathVariable UUID id) {
        Optional<DeskBooking> deskBooking = deskBookingService.getDeskBookingById(id);

        if (deskBooking.isPresent()) {
            return new ResponseEntity<>(deskBooking.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The desk booking with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-status")
    
    public ResponseEntity<List<DeskBooking>> getBookingsByStatus(@RequestParam DeskBookingStatus status) {
        return new ResponseEntity<>(
            deskBookingService.getBookingsByStatus(status),
            HttpStatus.OK
        );
        }

    @GetMapping("/between")
    public ResponseEntity<List<DeskBooking>> getBookingsBetween(
        @RequestParam String start,
        @RequestParam String end) {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);
            return new ResponseEntity<>(
                deskBookingService.getBookingsBetween(startDate, endDate),
                HttpStatus.OK
            );
        }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDeskBooking(@PathVariable UUID id, @RequestBody DeskBooking deskBooking) {
        Optional<DeskBooking> updatedDeskBooking = deskBookingService.updateDeskBooking(id, deskBooking);

        if (updatedDeskBooking.isPresent()) {
            return new ResponseEntity<>(updatedDeskBooking.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The desk booking with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDeskBooking(@PathVariable UUID id) {
        String message = deskBookingService.deleteDeskBooking(id);

        if (message.equals("Desk booking deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}