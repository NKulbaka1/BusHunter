package ru.kulbaka.bushunter.dto.route;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequest {
    @NotBlank
    private String departurePoint;

    @NotBlank
    private String destinationPoint;

    @NotNull
    private Long carrierId;

    @Positive
    @NotNull
    private Integer durationMinutes;
}