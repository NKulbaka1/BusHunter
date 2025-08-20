package ru.kulbaka.bushunter.dto.ticket;

import ru.kulbaka.bushunter.dto.route.RouteResponse;

import java.time.LocalDateTime;

public record TicketResponse(
        Long id,
        RouteResponse route,
        LocalDateTime departureDateTime,
        String seatNumber,
        double price
) {}
