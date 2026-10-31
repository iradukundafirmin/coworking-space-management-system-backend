package com.coworking.coworkingspacemanagementsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.coworking.coworkingspacemanagementsystem.entity.Host;
import com.coworking.coworkingspacemanagementsystem.repository.HostRepository;

@Service
public class HostService {

    private final HostRepository hostRepository;

    public HostService(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    public String saveHost(Host host) {
        hostRepository.save(host);
        return "Host saved successfully";
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
}