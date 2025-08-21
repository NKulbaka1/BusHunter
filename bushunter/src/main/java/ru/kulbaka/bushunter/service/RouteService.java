package ru.kulbaka.bushunter.service;

import ru.kulbaka.bushunter.dto.route.RouteRequest;
import ru.kulbaka.bushunter.dto.route.RouteResponse;

import java.util.List;

public interface RouteService {
    List<RouteResponse> getAllRoutes();
    RouteResponse getRouteById(Long id);
    RouteResponse createRoute(RouteRequest request);
    RouteResponse updateRoute(Long id, RouteRequest request);
    RouteResponse deleteRoute(Long id);
}
