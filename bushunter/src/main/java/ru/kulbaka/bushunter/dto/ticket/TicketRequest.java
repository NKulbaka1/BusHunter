package ru.kulbaka.bushunter.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record TicketRequest(
        @NotNull Long routeId,
        @NotNull LocalDateTime departureDateTime,
        @NotBlank String seatNumber,
        @Positive double price
) {}
