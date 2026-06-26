package com.infraops.infraops_backend.service;

import com.infraops.infraops_backend.dto.AssetRequest;
import com.infraops.infraops_backend.dto.AssetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssetService {
    void createAsset(AssetRequest request);
    Page<AssetResponse> getAllAssets(Pageable pageable);
    Page<AssetResponse> searchAssets(String query, Pageable pageable);
    AssetResponse getAssetById(String id);
    void updateAsset(String id, AssetRequest request);
    void deleteAsset(String id);
}
