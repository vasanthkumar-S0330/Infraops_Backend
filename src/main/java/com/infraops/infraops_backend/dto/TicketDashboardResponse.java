package com.infraops.infraops_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDashboardResponse {
    private long totalTickets;
    private long open;
    private long inProgress;
    private long resolved;
    private long closed;
    private long highPriority;
    private long mediumPriority;
    private long lowPriority;
}
