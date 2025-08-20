package ru.kulbaka.bushunter.model;

import java.time.LocalDateTime;

public record TicketSearchParams(
        LocalDateTime dateFrom,
        LocalDateTime dateTo,
        String departurePoint,
        String destinationPoint,
        String carrierName
) {
    public TicketSearchParams {
        if (dateFrom != null && dateTo != null
                && dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("Дата 'от' не может быть позже даты 'до'");
        }
    }
}
