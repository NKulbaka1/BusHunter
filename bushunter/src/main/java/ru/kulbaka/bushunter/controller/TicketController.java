package ru.kulbaka.bushunter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kulbaka.bushunter.dto.ticket.TicketRequest;
import ru.kulbaka.bushunter.dto.ticket.TicketResponse;
import ru.kulbaka.bushunter.model.TicketSearchParams;
import ru.kulbaka.bushunter.service.TicketService;

//import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/get-all-available")
    public List<TicketResponse> getAvailableTickets(
            @RequestParam(required = false) LocalDateTime dateFrom,
            @RequestParam(required = false) LocalDateTime dateTo,
            @RequestParam(required = false) String departurePoint,
            @RequestParam(required = false) String destinationPoint,
            @RequestParam(required = false) String carrierName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ticketService.getAvailableTickets(
                new TicketSearchParams(dateFrom, dateTo, departurePoint, destinationPoint, carrierName),
                page,
                size
        );
    }

    @PostMapping("/{ticketId}/purchase")
    public void purchaseTicket(@PathVariable Long ticketId/*,  Principal principal*/) {
        //Long userId = getUserIdFromPrincipal(principal);
        Long userId = 1L;
        ticketService.purchaseTicket(ticketId, userId);
    }

    @GetMapping
    public List<TicketResponse> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketResponse getTicket(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse createTicket(@Valid @RequestBody TicketRequest request) {
        return ticketService.createTicket(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TicketResponse updateTicket(@PathVariable Long id, @Valid @RequestBody TicketRequest request) {
        return ticketService.updateTicket(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TicketResponse deleteTicket(@PathVariable Long id) {
        return ticketService.deleteTicket(id);
    }
}
