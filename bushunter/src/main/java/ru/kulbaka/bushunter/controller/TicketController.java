package ru.kulbaka.bushunter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kulbaka.bushunter.dto.ticket.TicketRequest;
import ru.kulbaka.bushunter.dto.ticket.TicketResponse;
import ru.kulbaka.bushunter.model.TicketSearchParams;
import ru.kulbaka.bushunter.security.util.AuthenticationUtils;
import ru.kulbaka.bushunter.service.TicketService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@Tag(name = "Tickets")
public class TicketController {
    private final TicketService ticketService;
    private final AuthenticationUtils authUtils;

    @Operation(
            summary = "Поиск доступных билетов",
            description = "Поиск билетов по различным параметрам фильтрации"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список билетов успешно получен"
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @GetMapping("/get-all-available")
    public List<TicketResponse> getAvailableTickets(
            @Parameter(description = "Дата отправления от") @RequestParam(required = false) LocalDateTime dateFrom,
            @Parameter(description = "Дата отправления до") @RequestParam(required = false) LocalDateTime dateTo,
            @Parameter(description = "Пункт отправления") @RequestParam(required = false) String departurePoint,
            @Parameter(description = "Пункт назначения") @RequestParam(required = false) String destinationPoint,
            @Parameter(description = "Название перевозчика") @RequestParam(required = false) String carrierName,
            @Parameter(description = "Номер страницы") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "10") int size) {
        return ticketService.getAvailableTickets(
                new TicketSearchParams(dateFrom, dateTo, departurePoint, destinationPoint, carrierName),
                page,
                size
        );
    }

    @Operation(
            summary = "Покупка билета",
            description = "Покупка билета текущим пользователем"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Билет успешно куплен",
            content = @Content(schema = @Schema(implementation = TicketResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Билет уже куплен"
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Билет не найден"
    )
    @PostMapping("/{ticketId}/purchase")
    public TicketResponse purchaseTicket(@PathVariable Long ticketId, Principal principal) {
        Long userId = authUtils.getCurrentUserId(principal);
        return ticketService.purchaseTicket(ticketId, userId);
    }

    @Operation(
            summary = "Получить мои билеты",
            description = "Получение списка билетов текущего пользователя"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список билетов успешно получен"
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @GetMapping("/my-tickets")
    public List<TicketResponse> getUserTickets(Principal principal) {
        Long userId = authUtils.getCurrentUserId(principal);
        return ticketService.getUserTickets(userId);
    }

    @Operation(
            summary = "Получить все билеты",
            description = "Получение списка всех билетов (включая купленные)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список билетов успешно получен"
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @GetMapping
    public List<TicketResponse> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @Operation(
            summary = "Получить билет по ID",
            description = "Получение информации о конкретном билете"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Билет найден",
            content = @Content(schema = @Schema(implementation = TicketResponse.class))
    )
    @ApiResponse(
            responseCode = "401",
            description = "Ошибка аутентификации"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Билет не найден"
    )
    @GetMapping("/{id}")
    public TicketResponse getTicket(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @Operation(
            summary = "Создать билет",
            description = "Создание нового билета (только для администраторов)"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Билет успешно создан",
            content = @Content(schema = @Schema(implementation = TicketResponse.class))
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
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse createTicket(@Valid @RequestBody TicketRequest request) {
        return ticketService.createTicket(request);
    }

    @Operation(
            summary = "Обновить билет",
            description = "Обновление информации о билете (только для администраторов)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Билет успешно обновлен",
            content = @Content(schema = @Schema(implementation = TicketResponse.class))
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
            description = "Билет не найден"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TicketResponse updateTicket(@PathVariable Long id, @Valid @RequestBody TicketRequest request) {
        return ticketService.updateTicket(id, request);
    }

    @Operation(
            summary = "Удалить билет",
            description = "Удаление билета (только для администраторов)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Билет успешно удален",
            content = @Content(schema = @Schema(implementation = TicketResponse.class))
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
            description = "Билет не найден"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TicketResponse deleteTicket(@PathVariable Long id) {
        return ticketService.deleteTicket(id);
    }
}
