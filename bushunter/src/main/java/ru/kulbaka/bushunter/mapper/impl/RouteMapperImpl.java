package ru.kulbaka.bushunter.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kulbaka.bushunter.dto.route.RouteRequest;
import ru.kulbaka.bushunter.dto.route.RouteResponse;
import ru.kulbaka.bushunter.mapper.CarrierMapper;
import ru.kulbaka.bushunter.mapper.RouteMapper;
import ru.kulbaka.bushunter.model.Carrier;
import ru.kulbaka.bushunter.model.Route;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class RouteMapperImpl implements RouteMapper {

    private final CarrierMapper carrierMapper;

    @Override
    public Route toModel(RouteRequest request) {
        if (request == null) {
            return null;
        }

        Route route = new Route();

        Carrier carrier = new Carrier();
        carrier.setId(request.getCarrierId());
        route.setCarrier(carrier);

        route.setDeparturePoint(request.getDeparturePoint());
        route.setDestinationPoint(request.getDestinationPoint());
        route.setDurationMinutes(request.getDurationMinutes());

        return route;
    }

    @Override
    public RouteResponse toResponse(Route route) {
        if (route == null) return null;

        return new RouteResponse(
                route.getId(),
                route.getDeparturePoint(),
                route.getDestinationPoint(),
                carrierMapper.toResponse(route.getCarrier()),
                route.getDurationMinutes()
        );
    }

    @Override
    public Route mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Route route = new Route();
        route.setId(resultSet.getLong("route_id"));
        route.setDeparturePoint(resultSet.getString("departure_point"));
        route.setDestinationPoint(resultSet.getString("destination_point"));
        route.setDurationMinutes(resultSet.getInt("duration_minutes"));

        Carrier carrier = carrierMapper.mapRow(resultSet, rowNum);
        route.setCarrier(carrier);

        return route;
    }
}
