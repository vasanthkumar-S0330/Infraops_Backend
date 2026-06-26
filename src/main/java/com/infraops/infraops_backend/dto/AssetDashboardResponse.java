package com.infraops.infraops_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetDashboardResponse {
    private long totalAssets;
    private long activeAssets;
    private long inactiveAssets;
    private long maintenanceAssets;
    private long retiredAssets;
    private List<LocationCount> assetsByLocation;
}
