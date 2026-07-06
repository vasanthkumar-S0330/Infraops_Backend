package com.infraops.infraops_backend.controller;

import com.infraops.infraops_backend.dto.ServerRequest;
import com.infraops.infraops_backend.model.Server;
import com.infraops.infraops_backend.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @GetMapping
    public ResponseEntity<List<Server>> getAllServers() {
        return ResponseEntity.ok(serverService.getAllServers());
    }

    @PostMapping
    public ResponseEntity<Server> addServer(@RequestBody ServerRequest request) {
        return ResponseEntity.ok(serverService.addServer(request));
    }
}
