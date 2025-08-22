package ru.kulbaka.bushunter.mapper;

import ru.kulbaka.bushunter.dto.ticket.TicketRequest;
import ru.kulbaka.bushunter.dto.ticket.TicketResponse;
import ru.kulbaka.bushunter.kafka.model.TicketPurchaseEvent;
import ru.kulbaka.bushunter.model.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface TicketMapper {
    Ticket toModel(TicketRequest request);

    TicketResponse toResponse(Ticket ticket);

    TicketPurchaseEvent toEvent(Ticket ticket);

    Ticket mapRow(ResultSet resultSet, int rowNum) throws SQLException;
}
