package com.coworking.coworkingspacemanagementsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.coworking.coworkingspacemanagementsystem.entity.Facility;
import com.coworking.coworkingspacemanagementsystem.repository.FacilityRepository;

@Service
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public String saveFacility(Facility facility) {
        facilityRepository.save(facility);
        return "Facility saved successfully";
    }

    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    public Optional<Facility> getFacilityById(UUID id) {
        return facilityRepository.findById(id);
    }

    public List<Facility> getUnusedFacilities() {
        return facilityRepository.findUnusedFacilities();
    }

    public Optional<Facility> updateFacility(UUID id, Facility facility) {
        Optional<Facility> existingFacility = facilityRepository.findById(id);

        if (existingFacility.isPresent()) {
            facility.setId(id);
            return Optional.of(facilityRepository.save(facility));
        }

        return Optional.empty();
    }

    public String deleteFacility(UUID id) {
        Optional<Facility> facility = facilityRepository.findById(id);

        if (facility.isPresent()) {
            facilityRepository.delete(facility.get());
            return "Facility deleted successfully";
        }

        return "The facility with that id does not exist";
    }
}