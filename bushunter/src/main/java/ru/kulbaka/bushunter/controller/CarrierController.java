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
import ru.kulbaka.bushunter.dto.carrier.CarrierRequest;
import ru.kulbaka.bushunter.dto.carrier.CarrierResponse;
import ru.kulbaka.bushunter.service.CarrierService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carriers")
@RequiredArgsConstructor
@Tag(name = "Carriers")
public class CarrierController {
    private final CarrierService carrierService;

    @Operation(
            summary = "Получить всех перевозчиков",
            description = "Возвращает список всех перевозчиков"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список перевозчиков успешно получен"
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @GetMapping
    public List<CarrierResponse> getAllCarriers() {
        return carrierService.getAllCarriers();
    }

    @Operation(
            summary = "Получить перевозчика по ID",
            description = "Возвращает информацию о конкретном перевозчике"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Перевозчик найден",
            content = @Content(schema = @Schema(implementation = CarrierResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Перевозчик не найден"
    )
    @GetMapping("/{id}")
    public CarrierResponse getCarrier(@PathVariable Long id) {
        return carrierService.getCarrierById(id);
    }

    @Operation(
            summary = "Создать перевозчика",
            description = "Создание нового перевозчика (только для администраторов)"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Перевозчик успешно создан",
            content = @Content(schema = @Schema(implementation = CarrierResponse.class))
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
    public CarrierResponse createCarrier(@Valid @RequestBody CarrierRequest request) {
        return carrierService.createCarrier(request);
    }

    @Operation(
            summary = "Обновить перевозчика",
            description = "Обновление информации о перевозчике (только для администраторов)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Перевозчик успешно обновлен",
            content = @Content(schema = @Schema(implementation = CarrierResponse.class))
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
            description = "Перевозчик не найден"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CarrierResponse updateCarrier(@PathVariable Long id, @Valid @RequestBody CarrierRequest request) {
        return carrierService.updateCarrier(id, request);
    }

    @Operation(
            summary = "Удалить перевозчика",
            description = "Удаление перевозчика (только для администраторов)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Перевозчик успешно удален",
            content = @Content(schema = @Schema(implementation = CarrierResponse.class))
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
            description = "Перевозчик не найден"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CarrierResponse deleteCarrier(@PathVariable Long id) {
        return carrierService.deleteCarrier(id);
    }
}
