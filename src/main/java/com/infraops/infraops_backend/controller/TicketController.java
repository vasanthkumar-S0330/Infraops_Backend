package com.infraops.infraops_backend.controller;

import com.infraops.infraops_backend.dto.MessageResponse;
import com.infraops.infraops_backend.dto.TicketRequest;
import com.infraops.infraops_backend.dto.TicketResponse;
import com.infraops.infraops_backend.dto.TicketStatusUpdateRequest;
import com.infraops.infraops_backend.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<MessageResponse> createTicket(@Valid @RequestBody TicketRequest request, Authentication authentication) {
        String createdBy = authentication.getName(); // Extracts email from JWT
        ticketService.createTicket(request, createdBy);
        return ResponseEntity.ok(new MessageResponse("Ticket created successfully"));
    }

    @GetMapping
    public ResponseEntity<Page<TicketResponse>> getAllTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(ticketService.getAllTickets(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable String id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateTicket(@PathVariable String id, @Valid @RequestBody TicketRequest request) {
        ticketService.updateTicket(id, request);
        return ResponseEntity.ok(new MessageResponse("Ticket updated successfully"));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MessageResponse> updateTicketStatus(@PathVariable String id, @Valid @RequestBody TicketStatusUpdateRequest request) {
        ticketService.updateTicketStatus(id, request.getStatus());
        return ResponseEntity.ok(new MessageResponse("Ticket status updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteTicket(@PathVariable String id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok(new MessageResponse("Ticket deleted successfully"));
    }
}
