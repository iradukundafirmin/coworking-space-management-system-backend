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

import com.coworking.coworkingspacemanagementsystem.entity.Floor;
import com.coworking.coworkingspacemanagementsystem.service.FloorService;

@RestController
@RequestMapping("/api/floors")
public class FloorController {

    private final FloorService floorService;

    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveFloor(@RequestBody Floor floor) {
        String message = floorService.saveFloor(floor);

        if (message.equals("Floor saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Floor>> getAllFloors() {
        return new ResponseEntity<>(floorService.getAllFloors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFloorById(@PathVariable UUID id) {
        Optional<Floor> floor = floorService.getFloorById(id);

        if (floor.isPresent()) {
            return new ResponseEntity<>(floor.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The floor with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFloor(@PathVariable UUID id, @RequestBody Floor floor) {
        Optional<Floor> updatedFloor = floorService.updateFloor(id, floor);

        if (updatedFloor.isPresent()) {
            return new ResponseEntity<>(updatedFloor.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The floor with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFloor(@PathVariable UUID id) {
        String message = floorService.deleteFloor(id);

        if (message.equals("Floor deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}