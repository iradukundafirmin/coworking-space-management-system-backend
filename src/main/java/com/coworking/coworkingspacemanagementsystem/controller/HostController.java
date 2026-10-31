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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coworking.coworkingspacemanagementsystem.entity.Host;
import com.coworking.coworkingspacemanagementsystem.service.HostService;

@RestController
@RequestMapping("/api/hosts")
public class HostController {

    private final HostService hostService;

    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveHost(@RequestBody Host host) {
        String message = hostService.saveHost(host);

        if (message.equals("Host saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Host>> getAllHosts() {
        return new ResponseEntity<>(hostService.getAllHosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHostById(@PathVariable UUID id) {
        Optional<Host> host = hostService.getHostById(id);

        if (host.isPresent()) {
            return new ResponseEntity<>(host.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The host with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-facility")
    public ResponseEntity<List<Host>> getHostsByFacilityName(@RequestParam String name) {
        return new ResponseEntity<>(
            hostService.getHostsByFacilityName(name),
            HttpStatus.OK
    );
}

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHost(@PathVariable UUID id, @RequestBody Host host) {
        Optional<Host> updatedHost = hostService.updateHost(id, host);

        if (updatedHost.isPresent()) {
            return new ResponseEntity<>(updatedHost.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The host with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHost(@PathVariable UUID id) {
        String message = hostService.deleteHost(id);

        if (message.equals("Host deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}