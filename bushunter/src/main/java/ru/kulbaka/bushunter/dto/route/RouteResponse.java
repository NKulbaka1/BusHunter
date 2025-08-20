package ru.kulbaka.bushunter.dto.route;

import ru.kulbaka.bushunter.dto.carrier.CarrierResponse;

public record RouteResponse(
        Long id,
        String departurePoint,
        String destinationPoint,
        CarrierResponse carrier,
        Integer durationMinutes
) {}
