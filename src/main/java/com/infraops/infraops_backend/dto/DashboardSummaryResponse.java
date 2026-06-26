package com.infraops.infraops_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummaryResponse {
    private long totalUsers;
    private long totalAssets;
    private long totalTickets;
    private long openTickets;
    private long closedTickets;
    private long activeAssets;
    private long inactiveAssets;
}
