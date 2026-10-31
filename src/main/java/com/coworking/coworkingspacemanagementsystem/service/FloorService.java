package com.coworking.coworkingspacemanagementsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.coworking.coworkingspacemanagementsystem.entity.Floor;
import com.coworking.coworkingspacemanagementsystem.repository.FloorRepository;

@Service
public class FloorService {

    private final FloorRepository floorRepository;

    public FloorService(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    public String saveFloor(Floor floor) {
        floorRepository.save(floor);
        return "Floor saved successfully";
    }

    public List<Floor> getFreeFloors() {
        return floorRepository.findFreeFloors();
    }

    public List<Floor> getAllFloors() {
        return floorRepository.findAll();
    }

    public Optional<Floor> getFloorById(UUID id) {
        return floorRepository.findById(id);
    }

    public Optional<Floor> updateFloor(UUID id, Floor floor) {
        if (floorRepository.existsById(id)) {
            floor.setId(id);
            return Optional.of(floorRepository.save(floor));
        }
        return Optional.empty();
    }

    public String deleteFloor(UUID id) {
        Optional<Floor> floor = floorRepository.findById(id);

        if (floor.isPresent()) {
            floorRepository.delete(floor.get());
            return "Floor deleted successfully";
        }

        return "The floor with that id does not exist";
    }
}