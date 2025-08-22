package ru.kulbaka.bushunter.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TicketPurchaseEvent {
    private Long originalTicketId;
    private String seatNumber;
    private Double price;
    private LocalDateTime departureDateTime;
    private Long userId;
    private Long routeId;
    private Long carrierId;
}