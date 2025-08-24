package ru.kulbaka.bushunter.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.bushunter.dto.route.RouteResponse;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private Long id;
    private RouteResponse route;
    private LocalDateTime departureDateTime;
    private String seatNumber;
    private double price;
}