package ru.kulbaka.bushunter.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ticket {
    private Long id;
    private Route route;
    private LocalDateTime departureDateTime;
    private String seatNumber;
    private Double price;
    private Long userId;
}
