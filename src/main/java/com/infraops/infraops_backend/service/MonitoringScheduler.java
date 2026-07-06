package com.infraops.infraops_backend.service;

import com.infraops.infraops_backend.model.Server;
import com.infraops.infraops_backend.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonitoringScheduler {

    private final ServerRepository serverRepository;
    private final Random random = new Random();

    @Scheduled(fixedRate = 10000) // Run every 10 seconds
    public void updateServerMetrics() {
        List<Server> servers = serverRepository.findAll();
        if (servers.isEmpty()) {
            return;
        }
        
        log.debug("Updating metrics for {} servers", servers.size());
        
        for (Server server : servers) {
            // Simulate realistic metric fluctuations
            int currentCpu = server.getCpu();
            int cpuChange = random.nextInt(11) - 5; // -5 to +5
            int newCpu = Math.max(0, Math.min(100, currentCpu == 0 ? random.nextInt(30) + 10 : currentCpu + cpuChange));
            
            int currentMem = server.getMemory();
            int memChange = random.nextInt(7) - 3; // -3 to +3
            int newMem = Math.max(0, Math.min(100, currentMem == 0 ? random.nextInt(40) + 30 : currentMem + memChange));
            
            int currentDisk = server.getDisk();
            int diskChange = random.nextInt(3) - 1; // slow disk changes
            int newDisk = Math.max(0, Math.min(100, currentDisk == 0 ? random.nextInt(60) + 20 : currentDisk + diskChange));
            
            server.setCpu(newCpu);
            server.setMemory(newMem);
            server.setDisk(newDisk);
            
            // Calculate status
            if ("DOWN".equals(server.getStatus())) {
                // 30% chance to recover
                if (random.nextInt(100) < 30) {
                    server.setStatus("UP");
                }
            } else {
                // 1% chance to go down randomly
                if (random.nextInt(100) < 1) {
                    server.setStatus("DOWN");
                    server.setCpu(0);
                    server.setMemory(0);
                } else if (newCpu >= 90 || newMem >= 90 || newDisk >= 90) {
                    server.setStatus("WARNING");
                } else {
                    server.setStatus("UP");
                }
            }
        }
        
        serverRepository.saveAll(servers);
    }
}
