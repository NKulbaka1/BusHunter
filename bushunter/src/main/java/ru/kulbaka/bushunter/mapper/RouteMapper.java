package ru.kulbaka.bushunter.mapper;

import ru.kulbaka.bushunter.dto.route.RouteRequest;
import ru.kulbaka.bushunter.dto.route.RouteResponse;
import ru.kulbaka.bushunter.model.Route;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RouteMapper {
    Route toModel(RouteRequest request);

    RouteResponse toResponse(Route route);

    Route mapRow(ResultSet resultSet, int rowNum) throws SQLException;
}
