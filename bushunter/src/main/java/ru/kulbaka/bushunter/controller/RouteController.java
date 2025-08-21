package ru.kulbaka.bushunter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kulbaka.bushunter.dto.route.RouteRequest;
import ru.kulbaka.bushunter.dto.route.RouteResponse;
import ru.kulbaka.bushunter.service.RouteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping
    public List<RouteResponse> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public RouteResponse getRoute(@PathVariable Long id) {
        return routeService.getRouteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RouteResponse createRoute(@Valid @RequestBody RouteRequest request) {
        return routeService.createRoute(request);
    }

    @PutMapping("/{id}")
    public RouteResponse updateRoute(@PathVariable Long id, @Valid @RequestBody RouteRequest request) {
        return routeService.updateRoute(id, request);
    }

    @DeleteMapping("/{id}")
    public RouteResponse deleteRoute(@PathVariable Long id) {
        return routeService.deleteRoute(id);
    }
}
