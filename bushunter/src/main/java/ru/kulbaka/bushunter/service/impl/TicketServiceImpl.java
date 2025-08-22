package ru.kulbaka.bushunter.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kulbaka.bushunter.dao.RouteDao;
import ru.kulbaka.bushunter.dao.TicketDao;
import ru.kulbaka.bushunter.dto.ticket.TicketRequest;
import ru.kulbaka.bushunter.dto.ticket.TicketResponse;
import ru.kulbaka.bushunter.exception.EntityNotFoundException;
import ru.kulbaka.bushunter.exception.TicketAlreadyPurchasedException;
import ru.kulbaka.bushunter.kafka.model.TicketPurchaseEvent;
import ru.kulbaka.bushunter.kafka.produser.TicketPurchaseProducer;
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
    private final TicketPurchaseProducer ticketPurchaseProducer;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public TicketResponse purchaseTicket(Long ticketId, Long userId) {
        Ticket ticket = getTicketModelById(ticketId);
        if (ticket.getUserId() != null) {
            throw new TicketAlreadyPurchasedException(ticketId);
        }

        ticketDao.purchaseTicket(ticketId, userId);

        ticket.setUserId(userId);

        TicketPurchaseEvent event = ticketMapper.toEvent(ticket);
        ticketPurchaseProducer.sendTicketPurchaseEvent(event);

        return ticketMapper.toResponse(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getUserTickets(Long userId) {
        return ticketDao.findByUserId(userId).stream()
                .map(ticketMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getAllTickets() {
        return ticketDao.findAll().stream()
                .map(ticketMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Билет с айди " + id + " не найден"));
        return ticketMapper.toResponse(ticket);
    }

    @Override
    @Transactional
    public TicketResponse createTicket(TicketRequest request) {
        if (!routeDao.existsById(request.routeId())) {
            throw new EntityNotFoundException("Маршрут с айди " + request.routeId() + " не найден");
        }

        Ticket ticket = ticketMapper.toModel(request);
        Long id = ticketDao.create(ticket);

        Ticket savedTicket = getTicketModelById(id);
        return ticketMapper.toResponse(savedTicket);
    }

    @Override
    @Transactional
    public TicketResponse updateTicket(Long id, TicketRequest request) {
        if (!ticketDao.existsById(id)) {
            throw new EntityNotFoundException("Билет с айди " + id + " не найден");
        }

        if (!routeDao.existsById(request.routeId())) {
            throw new EntityNotFoundException("Маршрут с айди " + request.routeId() + " не найден");
        }

        Ticket ticket = ticketMapper.toModel(request);
        ticket.setId(id);
        ticketDao.update(ticket);

        return getTicketById(id);
    }

    @Override
    @Transactional
    public TicketResponse deleteTicket(Long id) {
        Ticket ticket = getTicketModelById(id);

        ticketDao.delete(id);

        return ticketMapper.toResponse(ticket);
    }

    private Ticket getTicketModelById(Long id) {
        return ticketDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Билет с айди " + id + " не найден"));
    }
}
