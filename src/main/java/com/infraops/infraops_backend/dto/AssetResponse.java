package com.infraops.infraops_backend.dto;

import com.infraops.infraops_backend.model.AssetStatus;
import com.infraops.infraops_backend.model.AssetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetResponse {
    private String id;
    private String name;
    private AssetType type;
    private String ipAddress;
    private String location;
    private AssetStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
