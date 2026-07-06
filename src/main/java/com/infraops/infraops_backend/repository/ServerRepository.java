package com.infraops.infraops_backend.repository;

import com.infraops.infraops_backend.model.Server;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends MongoRepository<Server, String> {
}
