package com.coworking.coworkingspacemanagementsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.coworking.coworkingspacemanagementsystem.entity.Facility;
import com.coworking.coworkingspacemanagementsystem.entity.Floor;
import com.coworking.coworkingspacemanagementsystem.entity.Host;
import com.coworking.coworkingspacemanagementsystem.repository.FacilityRepository;
import com.coworking.coworkingspacemanagementsystem.repository.FloorRepository;
import com.coworking.coworkingspacemanagementsystem.repository.HostRepository;


@Service
public class HostService {

    private final HostRepository hostRepository;
    private final FacilityRepository facilityRepository;
    private final FloorRepository floorRepository;

    public HostService(
        HostRepository hostRepository,
        FacilityRepository facilityRepository,
        FloorRepository floorRepository) {

    this.hostRepository = hostRepository;
    this.facilityRepository = facilityRepository;
    this.floorRepository = floorRepository;
}
    public String saveHost(Host host) {
        hostRepository.save(host);
        return "Host saved successfully";
    }

    public List<Host> getHostsByFacilityName(String name) {
        return hostRepository.findHostsByFacilityName(name);
    }

    public List<Host> getAllHosts() {
        return hostRepository.findAll();
    }

    public Optional<Host> getHostById(UUID id) {
        return hostRepository.findById(id);
    }

    public Optional<Host> updateHost(UUID id, Host host) {
        if (hostRepository.existsById(id)) {
            host.setId(id);
            return Optional.of(hostRepository.save(host));
        }
        return Optional.empty();
    }

    public String deleteHost(UUID id) {
        Optional<Host> host = hostRepository.findById(id);

        if (host.isPresent()) {
            hostRepository.delete(host.get());
            return "Host deleted successfully";
        }

        return "The host with that id does not exist";
    }

    public String addFacilityToHost(UUID hostId, UUID facilityId) {
        Optional<Host> optionalHost = hostRepository.findById(hostId);
        
        if (optionalHost.isEmpty()) {
            return "The host with that id does not exist";
        }
        Optional<Facility> optionalFacility = facilityRepository.findById(facilityId);

        if (optionalFacility.isEmpty()) {
            return "That facility does not exist";
        }
        
        Host host = optionalHost.get();
        Facility facility = optionalFacility.get();
        
        if (host.getFacilities().contains(facility)) {
            return "The host already has that facility";
        }
        
        host.getFacilities().add(facility);
        hostRepository.save(host);
        
        return "Facility added to the host";
    }


    public String changeHostFloor(UUID hostId, int floorNumber) {
        
        Optional<Host> optionalHost = hostRepository.findById(hostId);
        if (optionalHost.isEmpty()) {
            return "The host with that id does not exist";
        }

    Optional<Floor> optionalFloor = floorRepository.findByFloorNumber(floorNumber);
    if (optionalFloor.isEmpty()) {
        return "The floor with that floorNumber does not exist";
    }
    
    Host host = optionalHost.get();
    Floor newFloor = optionalFloor.get();
    
    if (host.getFloor() != null && host.getFloor().getId().equals(newFloor.getId())) {
        return "Floor changed successfully";
    }

    if (hostRepository.existsByFloor(newFloor)) {
        return "That floor is taken by another host";
    }
    
    host.setFloor(newFloor);
    hostRepository.save(host);
    
    return "Floor changed successfully";
}
}