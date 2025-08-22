package ru.kulbaka.bushunter_kafka_consumer.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PurchasedTicket {
    private Long id;
    private Long originalTicketId;
    private String seatNumber;
    private Double price;
    private LocalDateTime departureDateTime;
    private Long userId;
    private Long routeId;
    private Long carrierId;
}
