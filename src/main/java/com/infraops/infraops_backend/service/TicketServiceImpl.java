package com.infraops.infraops_backend.service;

import com.infraops.infraops_backend.dto.TicketRequest;
import com.infraops.infraops_backend.dto.TicketResponse;
import com.infraops.infraops_backend.exception.ResourceNotFoundException;
import com.infraops.infraops_backend.model.Ticket;
import com.infraops.infraops_backend.model.TicketStatus;
import com.infraops.infraops_backend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void createTicket(TicketRequest request, String createdBy) {
        Ticket ticket = Ticket.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(request.getStatus())
                .assetId(request.getAssetId())
                .createdBy(createdBy)
                .build();
        ticketRepository.save(ticket);
    }

    @Override
    public Page<TicketResponse> getAllTickets(Pageable pageable) {
        return ticketRepository.findAll(pageable).map(this::mapToTicketResponse);
    }

    @Override
    public TicketResponse getTicketById(String id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        return mapToTicketResponse(ticket);
    }

    @Override
    public void updateTicket(String id, TicketRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setStatus(request.getStatus());
        ticket.setAssetId(request.getAssetId());

        ticketRepository.save(ticket);
    }

    @Override
    public void updateTicketStatus(String id, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        
        ticket.setStatus(status);
        ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(String id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found with id: " + id);
        }
        ticketRepository.deleteById(id);
    }

    private TicketResponse mapToTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .priority(ticket.getPriority())
                .status(ticket.getStatus())
                .assetId(ticket.getAssetId())
                .createdBy(ticket.getCreatedBy())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }
}
