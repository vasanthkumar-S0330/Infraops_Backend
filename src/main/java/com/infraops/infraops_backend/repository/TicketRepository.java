package com.infraops.infraops_backend.repository;

import com.infraops.infraops_backend.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    long countByStatus(com.infraops.infraops_backend.model.TicketStatus status);
    long countByPriority(com.infraops.infraops_backend.model.TicketPriority priority);
}
