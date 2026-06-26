package com.infraops.infraops_backend.dto;

import com.infraops.infraops_backend.model.AssetStatus;
import com.infraops.infraops_backend.model.AssetType;
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
public class AssetRequest {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Type is required")
    private AssetType type;
    
    @NotBlank(message = "IP Address is required")
    private String ipAddress;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @NotNull(message = "Status is required")
    private AssetStatus status;
}
