package ru.kulbaka.bushunter.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kulbaka.bushunter.dao.RouteDao;
import ru.kulbaka.bushunter.dao.TicketDao;
import ru.kulbaka.bushunter.dto.ticket.TicketRequest;
import ru.kulbaka.bushunter.dto.ticket.TicketResponse;
import ru.kulbaka.bushunter.exception.EntityNotFoundException;
import ru.kulbaka.bushunter.exception.TicketAlreadyPurchasedException;
import ru.kulbaka.bushunter.mapper.TicketMapper;
import ru.kulbaka.bushunter.model.Ticket;
import ru.kulbaka.bushunter.model.TicketSearchParams;
import ru.kulbaka.bushunter.service.TicketService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketDao ticketDao;
    private final RouteDao routeDao;
    private final TicketMapper ticketMapper;

    @Override
    public List<TicketResponse> getAvailableTickets(TicketSearchParams searchParams, int page, int size) {
        return ticketDao.findAvailableTickets(
                        searchParams.dateFrom(),
                        searchParams.dateTo(),
                        searchParams.departurePoint(),
                        searchParams.destinationPoint(),
                        searchParams.carrierName(),
                        page,
                        size
                ).stream()
                .map(ticketMapper::toResponse)
                .toList();
    }

    @Override
    public void purchaseTicket(Long ticketId, Long userId) {
        if (ticketDao.checkTicketPurchased(ticketId)) {
            throw new TicketAlreadyPurchasedException(ticketId);
        }
        ticketDao.purchaseTicket(ticketId, userId);
    }

    public List<TicketResponse> getAllTickets() {
        return ticketDao.findAll().stream()
                .map(ticketMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Билет не найден"));
        return ticketMapper.toResponse(ticket);
    }

    public TicketResponse createTicket(TicketRequest request) {
        if (!routeDao.existsById(request.routeId())) {
            throw new EntityNotFoundException("Маршрут не найден");
        }

        Ticket ticket = ticketMapper.toModel(request);
        Long id = ticketDao.create(ticket);
        ticket.setId(id);
        return ticketMapper.toResponse(ticket);
    }

    public TicketResponse updateTicket(Long id, TicketRequest request) {
        if (!ticketDao.existsById(id)) {
            throw new EntityNotFoundException("Билет не найден");
        }

        if (!routeDao.existsById(request.routeId())) {
            throw new EntityNotFoundException("Маршрут не найден");
        }

        Ticket ticket = ticketMapper.toModel(request);
        ticket.setId(id);
        ticketDao.update(ticket);

        return getTicketById(id);
    }

    public void deleteTicket(Long id) {
        if (!ticketDao.existsById(id)) {
            throw new EntityNotFoundException("Билет не найден");
        }
        ticketDao.delete(id);
    }

}
