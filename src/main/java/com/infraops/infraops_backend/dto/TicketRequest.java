package com.infraops.infraops_backend.dto;

import com.infraops.infraops_backend.model.TicketPriority;
import com.infraops.infraops_backend.model.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Priority is required")
    private TicketPriority priority;
    
    @NotNull(message = "Status is required")
    private TicketStatus status;

    private String assetId;
}
