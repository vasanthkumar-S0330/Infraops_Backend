package com.infraops.infraops_backend.service;

import com.infraops.infraops_backend.dto.*;
import com.infraops.infraops_backend.model.AssetStatus;
import com.infraops.infraops_backend.model.TicketPriority;
import com.infraops.infraops_backend.model.TicketStatus;
import com.infraops.infraops_backend.repository.AssetRepository;
import com.infraops.infraops_backend.repository.TicketRepository;
import com.infraops.infraops_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final TicketRepository ticketRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public DashboardSummaryResponse getDashboardSummary() {
        return DashboardSummaryResponse.builder()
                .totalUsers(userRepository.count())
                .totalAssets(assetRepository.count())
                .totalTickets(ticketRepository.count())
                .openTickets(ticketRepository.countByStatus(TicketStatus.OPEN))
                .closedTickets(ticketRepository.countByStatus(TicketStatus.CLOSED))
                .activeAssets(assetRepository.countByStatus(AssetStatus.ACTIVE))
                .inactiveAssets(assetRepository.countByStatus(AssetStatus.OFFLINE))
                .build();
    }

    @Override
    public AssetDashboardResponse getAssetDashboard() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("location").count().as("count"),
                Aggregation.project("count").and("_id").as("location")
        );

        AggregationResults<LocationCount> results = mongoTemplate.aggregate(aggregation, "assets", LocationCount.class);
        List<LocationCount> locationCounts = results.getMappedResults();

        return AssetDashboardResponse.builder()
                .totalAssets(assetRepository.count())
                .activeAssets(assetRepository.countByStatus(AssetStatus.ACTIVE))
                .inactiveAssets(assetRepository.countByStatus(AssetStatus.OFFLINE))
                .maintenanceAssets(assetRepository.countByStatus(AssetStatus.MAINTENANCE))
                .retiredAssets(assetRepository.countByStatus(AssetStatus.DECOMMISSIONED))
                .assetsByLocation(locationCounts)
                .build();
    }

    @Override
    public TicketDashboardResponse getTicketDashboard() {
        return TicketDashboardResponse.builder()
                .totalTickets(ticketRepository.count())
                .open(ticketRepository.countByStatus(TicketStatus.OPEN))
                .inProgress(ticketRepository.countByStatus(TicketStatus.IN_PROGRESS))
                .resolved(ticketRepository.countByStatus(TicketStatus.RESOLVED))
                .closed(ticketRepository.countByStatus(TicketStatus.CLOSED))
                .highPriority(ticketRepository.countByPriority(TicketPriority.HIGH) + ticketRepository.countByPriority(TicketPriority.CRITICAL))
                .mediumPriority(ticketRepository.countByPriority(TicketPriority.MEDIUM))
                .lowPriority(ticketRepository.countByPriority(TicketPriority.LOW))
                .build();
    }

    @Override
    public HealthDashboardResponse getHealthDashboard() {
        String mongoStatus = "CONNECTED";
        try {
            mongoTemplate.executeCommand("{ ping: 1 }");
        } catch (Exception e) {
            mongoStatus = "DISCONNECTED";
        }

        long uptimeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        String uptime = formatUptime(uptimeMillis);
        String serverTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return HealthDashboardResponse.builder()
                .application("UP")
                .database(mongoStatus)
                .mongodb(mongoStatus)
                .serverTime(serverTime)
                .uptime(uptime)
                .version("1.0.0")
                .build();
    }

    private String formatUptime(long uptimeMillis) {
        long days = uptimeMillis / (24 * 60 * 60 * 1000);
        long hours = (uptimeMillis / (60 * 60 * 1000)) % 24;
        long minutes = (uptimeMillis / (60 * 1000)) % 60;
        
        if (days > 0) return days + " Days";
        if (hours > 0) return hours + " Hours";
        if (minutes > 0) return minutes + " Minutes";
        return (uptimeMillis / 1000) + " Seconds";
    }
}
