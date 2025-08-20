package ru.kulbaka.bushunter.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kulbaka.bushunter.dao.CarrierDao;
import ru.kulbaka.bushunter.dao.RouteDao;
import ru.kulbaka.bushunter.dto.route.RouteRequest;
import ru.kulbaka.bushunter.dto.route.RouteResponse;
import ru.kulbaka.bushunter.exception.EntityNotFoundException;
import ru.kulbaka.bushunter.mapper.RouteMapper;
import ru.kulbaka.bushunter.model.Route;
import ru.kulbaka.bushunter.service.RouteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteDao routeDao;
    private final RouteMapper routeMapper;
    private final CarrierDao carrierDao;

    @Override
    public List<RouteResponse> getAllRoutes() {
        return routeDao.findAll().stream()
                .map(routeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RouteResponse getRouteById(Long id) {
        Route route = routeDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Маршрут не найден"));
        return routeMapper.toResponse(route);
    }

    @Override
    public RouteResponse createRoute(RouteRequest request) {
        if (!carrierDao.existsById(request.carrierId())) {
            throw new EntityNotFoundException("Перевозчик не найден");
        }

        Route route = routeMapper.toModel(request);
        Long id = routeDao.create(route);
        route.setId(id);
        return routeMapper.toResponse(route);
    }

    @Override
    public RouteResponse updateRoute(Long id, RouteRequest request) {
        if (!routeDao.existsById(id)) {
            throw new EntityNotFoundException("Маршрут не найден");
        }

        if (!carrierDao.existsById(request.carrierId())) {
            throw new EntityNotFoundException("Перевозчик не найден");
        }

        Route route = routeMapper.toModel(request);
        route.setId(id);
        routeDao.update(route);

        return getRouteById(id);
    }

    @Override
    public void deleteRoute(Long id) {
        if (!routeDao.existsById(id)) {
            throw new EntityNotFoundException("Маршрут не найден");
        }
        routeDao.delete(id);
    }
}
