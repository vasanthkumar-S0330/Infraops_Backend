package com.infraops.infraops_backend.dto;

import com.infraops.infraops_backend.model.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketStatusUpdateRequest {
    @NotNull(message = "Status is required")
    private TicketStatus status;
}
