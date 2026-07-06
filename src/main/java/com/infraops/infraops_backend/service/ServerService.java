package com.infraops.infraops_backend.service;

import com.infraops.infraops_backend.dto.ServerRequest;
import com.infraops.infraops_backend.model.Server;
import com.infraops.infraops_backend.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;

    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

    public Server addServer(ServerRequest request) {
        Server server = Server.builder()
                .name(request.getName())
                .ip(request.getIp())
                .os(request.getOs())
                .status("UP")
                .cpu(0)
                .memory(0)
                .disk(0)
                .uptime("0h 0m")
                .build();
        return serverRepository.save(server);
    }
}
