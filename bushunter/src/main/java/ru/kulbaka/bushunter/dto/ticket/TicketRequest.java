package ru.kulbaka.bushunter.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    @NotNull
    private Long routeId;

    @NotNull
    private LocalDateTime departureDateTime;

    @NotBlank
    private String seatNumber;

    @Positive
    private double price;
}