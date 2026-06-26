package com.infraops.infraops_backend.dto;

import com.infraops.infraops_backend.model.TicketPriority;
import com.infraops.infraops_backend.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private String id;
    private String title;
    private String description;
    private TicketPriority priority;
    private TicketStatus status;
    private String assetId;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
