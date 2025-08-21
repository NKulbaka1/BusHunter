package ru.kulbaka.bushunter.service;

import ru.kulbaka.bushunter.dto.ticket.TicketRequest;
import ru.kulbaka.bushunter.dto.ticket.TicketResponse;
import ru.kulbaka.bushunter.model.TicketSearchParams;

import java.util.List;

public interface TicketService {
    List<TicketResponse> getAvailableTickets(TicketSearchParams searchParams, int page, int size);
    void purchaseTicket(Long ticketId, Long userId);
    List<TicketResponse> getUserTickets(Long userId);
    List<TicketResponse> getAllTickets();
    TicketResponse getTicketById(Long id);
    TicketResponse createTicket(TicketRequest request);
    TicketResponse updateTicket(Long id, TicketRequest request);
    TicketResponse deleteTicket(Long id);
}
