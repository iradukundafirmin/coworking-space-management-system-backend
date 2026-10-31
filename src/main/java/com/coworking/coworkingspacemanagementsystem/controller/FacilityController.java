package com.coworking.coworkingspacemanagementsystem.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.coworking.coworkingspacemanagementsystem.entity.Facility;
import com.coworking.coworkingspacemanagementsystem.service.FacilityService;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveFacility(@RequestBody Facility facility) {
        String message = facilityService.saveFacility(facility);

        if (message.equals("Facility saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Facility>> getAllFacilities() {
        return new ResponseEntity<>(facilityService.getAllFacilities(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFacilityById(@PathVariable UUID id) {
        Optional<Facility> facility = facilityService.getFacilityById(id);

        if (facility.isPresent()) {
            return new ResponseEntity<>(facility.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "The facility with that id does not exist",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping("/unused")
    public ResponseEntity<List<Facility>> getUnusedFacilities() {
        return new ResponseEntity<>(
            facilityService.getUnusedFacilities(),
            HttpStatus.OK
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFacility(@PathVariable UUID id, @RequestBody Facility facility) {
        Optional<Facility> updatedFacility = facilityService.updateFacility(id, facility);

        if (updatedFacility.isPresent()) {
            return new ResponseEntity<>(updatedFacility.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    "The facility with that id does not exist",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFacility(@PathVariable UUID id) {
        String message = facilityService.deleteFacility(id);

        if (message.equals("Facility deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}