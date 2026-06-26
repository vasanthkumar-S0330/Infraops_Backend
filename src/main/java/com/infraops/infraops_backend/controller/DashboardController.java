package com.infraops.infraops_backend.controller;

import com.infraops.infraops_backend.dto.AssetDashboardResponse;
import com.infraops.infraops_backend.dto.DashboardSummaryResponse;
import com.infraops.infraops_backend.dto.HealthDashboardResponse;
import com.infraops.infraops_backend.dto.TicketDashboardResponse;
import com.infraops.infraops_backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> getSummary() {
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }

    @GetMapping("/assets")
    public ResponseEntity<AssetDashboardResponse> getAssetDashboard() {
        return ResponseEntity.ok(dashboardService.getAssetDashboard());
    }

    @GetMapping("/tickets")
    public ResponseEntity<TicketDashboardResponse> getTicketDashboard() {
        return ResponseEntity.ok(dashboardService.getTicketDashboard());
    }

    @GetMapping("/health")
    public ResponseEntity<HealthDashboardResponse> getHealthDashboard() {
        return ResponseEntity.ok(dashboardService.getHealthDashboard());
    }
}
