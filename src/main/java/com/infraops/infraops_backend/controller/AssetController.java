package com.infraops.infraops_backend.controller;

import com.infraops.infraops_backend.dto.AssetRequest;
import com.infraops.infraops_backend.dto.AssetResponse;
import com.infraops.infraops_backend.dto.MessageResponse;
import com.infraops.infraops_backend.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assets")
@PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping
    public ResponseEntity<MessageResponse> createAsset(@Valid @RequestBody AssetRequest request) {
        assetService.createAsset(request);
        return ResponseEntity.ok(new MessageResponse("Asset created successfully"));
    }

    @GetMapping
    public ResponseEntity<Page<AssetResponse>> getAllAssets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(assetService.getAllAssets(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AssetResponse>> searchAssets(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(assetService.searchAssets(query, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponse> getAssetById(@PathVariable String id) {
        return ResponseEntity.ok(assetService.getAssetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateAsset(@PathVariable String id, @Valid @RequestBody AssetRequest request) {
        assetService.updateAsset(id, request);
        return ResponseEntity.ok(new MessageResponse("Asset updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteAsset(@PathVariable String id) {
        assetService.deleteAsset(id);
        return ResponseEntity.ok(new MessageResponse("Asset deleted successfully"));
    }
}
