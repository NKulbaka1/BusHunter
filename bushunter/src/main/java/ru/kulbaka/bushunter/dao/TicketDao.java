package ru.kulbaka.bushunter.dao;

import ru.kulbaka.bushunter.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketDao {
    List<Ticket> findAvailableTickets(
            LocalDateTime dateFrom,
            LocalDateTime dateTo,
            String departurePoint,
            String destinationPoint,
            String carrierName,
            int page,
            int size
    );
    List<Ticket> findByUserId(Long userId);
    void purchaseTicket(Long ticketId, Long userId);
    boolean checkTicketPurchased(Long id);
    List<Ticket> findAll();
    Optional<Ticket> findById(Long id);
    Long create(Ticket ticket);
    void update(Ticket ticket);
    void delete(Long id);
    boolean existsById(Long id);
}
