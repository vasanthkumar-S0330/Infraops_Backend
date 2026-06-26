package com.infraops.infraops_backend.service;

import com.infraops.infraops_backend.dto.AssetRequest;
import com.infraops.infraops_backend.dto.AssetResponse;
import com.infraops.infraops_backend.exception.ResourceNotFoundException;
import com.infraops.infraops_backend.model.Asset;
import com.infraops.infraops_backend.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public void createAsset(AssetRequest request) {
        Asset asset = Asset.builder()
                .name(request.getName())
                .type(request.getType())
                .ipAddress(request.getIpAddress())
                .location(request.getLocation())
                .status(request.getStatus())
                .build();
        assetRepository.save(asset);
    }

    @Override
    public Page<AssetResponse> getAllAssets(Pageable pageable) {
        return assetRepository.findAll(pageable).map(this::mapToAssetResponse);
    }

    @Override
    public Page<AssetResponse> searchAssets(String query, Pageable pageable) {
        if (query == null || query.trim().isEmpty()) {
            return getAllAssets(pageable);
        }
        return assetRepository.findByNameContainingIgnoreCaseOrIpAddressContainingIgnoreCase(query.trim(), query.trim(), pageable)
                .map(this::mapToAssetResponse);
    }

    @Override
    public AssetResponse getAssetById(String id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
        return mapToAssetResponse(asset);
    }

    @Override
    public void updateAsset(String id, AssetRequest request) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));

        asset.setName(request.getName());
        asset.setType(request.getType());
        asset.setIpAddress(request.getIpAddress());
        asset.setLocation(request.getLocation());
        asset.setStatus(request.getStatus());

        assetRepository.save(asset);
    }

    @Override
    public void deleteAsset(String id) {
        if (!assetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asset not found with id: " + id);
        }
        assetRepository.deleteById(id);
    }

    private AssetResponse mapToAssetResponse(Asset asset) {
        return AssetResponse.builder()
                .id(asset.getId())
                .name(asset.getName())
                .type(asset.getType())
                .ipAddress(asset.getIpAddress())
                .location(asset.getLocation())
                .status(asset.getStatus())
                .createdAt(asset.getCreatedAt())
                .updatedAt(asset.getUpdatedAt())
                .build();
    }
}
