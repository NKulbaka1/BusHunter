package ru.kulbaka.bushunter.model;

import lombok.Data;

@Data
public class Route {
    private Long id;
    private String departurePoint;
    private String destinationPoint;
    private Carrier carrier;
    private Integer durationMinutes;
}
