package com.infraops.infraops_backend.repository;

import com.infraops.infraops_backend.model.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends MongoRepository<Asset, String> {
    Page<Asset> findByNameContainingIgnoreCaseOrIpAddressContainingIgnoreCase(String name, String ipAddress, Pageable pageable);
    long countByStatus(com.infraops.infraops_backend.model.AssetStatus status);
}
