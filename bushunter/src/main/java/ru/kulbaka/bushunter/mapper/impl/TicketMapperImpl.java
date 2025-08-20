package ru.kulbaka.bushunter.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kulbaka.bushunter.dto.ticket.TicketRequest;
import ru.kulbaka.bushunter.dto.ticket.TicketResponse;
import ru.kulbaka.bushunter.mapper.RouteMapper;
import ru.kulbaka.bushunter.mapper.TicketMapper;
import ru.kulbaka.bushunter.model.Route;
import ru.kulbaka.bushunter.model.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class TicketMapperImpl implements TicketMapper {
    private final RouteMapper routeMapper;

    @Override
    public Ticket toModel(TicketRequest request) {
        if (request == null) {
            return null;
        }

        Ticket ticket = new Ticket();

        Route route = new Route();
        route.setId(request.routeId());
        ticket.setRoute(route);

        ticket.setDepartureDateTime(request.departureDateTime());
        ticket.setSeatNumber(request.seatNumber());
        ticket.setPrice(request.price());

        return ticket;
    }

    @Override
    public TicketResponse toResponse(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        return new TicketResponse(
                ticket.getId(),
                routeMapper.toResponse(ticket.getRoute()),
                ticket.getDepartureDateTime(),
                ticket.getSeatNumber(),
                ticket.getPrice()
        );
    }

    @Override
    public Ticket mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getLong("ticket_id"));
        ticket.setDepartureDateTime(resultSet.getTimestamp("departure_date_time").toLocalDateTime());
        ticket.setSeatNumber(resultSet.getString("seat_number"));
        ticket.setPrice(resultSet.getDouble("price"));
        ticket.setUserId(resultSet.getObject("user_id", Long.class));

        Route route = routeMapper.mapRow(resultSet, rowNum);
        ticket.setRoute(route);

        return ticket;
    }
}
