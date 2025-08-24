package ru.kulbaka.bushunter.dto.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kulbaka.bushunter.dto.carrier.CarrierResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse {
    private Long id;
    private String departurePoint;
    private String destinationPoint;
    private CarrierResponse carrier;
    private Integer durationMinutes;
}