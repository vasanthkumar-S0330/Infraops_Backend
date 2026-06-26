package com.infraops.infraops_backend.service;

import com.infraops.infraops_backend.dto.AssetDashboardResponse;
import com.infraops.infraops_backend.dto.DashboardSummaryResponse;
import com.infraops.infraops_backend.dto.HealthDashboardResponse;
import com.infraops.infraops_backend.dto.TicketDashboardResponse;

public interface DashboardService {
    DashboardSummaryResponse getDashboardSummary();
    AssetDashboardResponse getAssetDashboard();
    TicketDashboardResponse getTicketDashboard();
    HealthDashboardResponse getHealthDashboard();
}
