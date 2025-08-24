package ru.kulbaka.bushunter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Маршруты")
public class RouteController {
    private final RouteService routeService;

    @Operation(
            summary = "Получить все маршруты",
            description = "Возвращает список всех маршрутов"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список маршрутов успешно получен"
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @GetMapping
    public List<RouteResponse> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @Operation(
            summary = "Получить маршрут по ID",
            description = "Возвращает информацию о конкретном маршруте"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Маршрут найден",
            content = @Content(schema = @Schema(implementation = RouteResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Маршрут не найден"
    )
    @GetMapping("/{id}")
    public RouteResponse getRoute(@PathVariable Long id) {
        return routeService.getRouteById(id);
    }

    @Operation(
            summary = "Создать маршрут",
            description = "Создание нового маршрута (только для администраторов)"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Маршрут успешно создан",
            content = @Content(schema = @Schema(implementation = RouteResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Неверные данные запроса"
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @ApiResponse(
            responseCode = "403",
            description = "Доступ запрещен"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public RouteResponse createRoute(@Valid @RequestBody RouteRequest request) {
        return routeService.createRoute(request);
    }

    @Operation(
            summary = "Обновить маршрут",
            description = "Обновление информации о маршруте (только для администраторов)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Маршрут успешно обновлен",
            content = @Content(schema = @Schema(implementation = RouteResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Неверные данные запроса"
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @ApiResponse(
            responseCode = "403",
            description = "Доступ запрещен"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Маршрут не найден"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RouteResponse updateRoute(@PathVariable Long id, @Valid @RequestBody RouteRequest request) {
        return routeService.updateRoute(id, request);
    }

    @Operation(
            summary = "Удалить маршрут",
            description = "Удаление маршрута (только для администраторов)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Маршрут успешно удален",
            content = @Content(schema = @Schema(implementation = RouteResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @ApiResponse(
            responseCode = "403",
            description = "Доступ запрещен"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Маршрут не найден"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RouteResponse deleteRoute(@PathVariable Long id) {
        return routeService.deleteRoute(id);
    }
}
