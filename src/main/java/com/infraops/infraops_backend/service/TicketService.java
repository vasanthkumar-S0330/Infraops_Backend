package com.infraops.infraops_backend.service;

import com.infraops.infraops_backend.dto.TicketRequest;
import com.infraops.infraops_backend.dto.TicketResponse;
import com.infraops.infraops_backend.model.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    void createTicket(TicketRequest request, String createdBy);
    Page<TicketResponse> getAllTickets(Pageable pageable);
    TicketResponse getTicketById(String id);
    void updateTicket(String id, TicketRequest request);
    void updateTicketStatus(String id, TicketStatus status);
    void deleteTicket(String id);
}
