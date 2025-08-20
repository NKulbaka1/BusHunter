package ru.kulbaka.bushunter.dto.route;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RouteRequest(
        @NotBlank String departurePoint,
        @NotBlank String destinationPoint,
        @NotNull Long carrierId,
        @Positive Integer durationMinutes
) {}
