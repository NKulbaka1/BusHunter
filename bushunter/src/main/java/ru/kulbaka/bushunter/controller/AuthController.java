package ru.kulbaka.bushunter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kulbaka.bushunter.security.dto.AuthRequest;
import ru.kulbaka.bushunter.security.dto.RefreshTokenRequest;
import ru.kulbaka.bushunter.security.dto.RegisterRequest;
import ru.kulbaka.bushunter.security.dto.TokenResponse;
import ru.kulbaka.bushunter.security.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Вход в систему",
            description = "Аутентификация пользователя и получение токенов доступа"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Успешная аутентификация",
            content = @Content(schema = @Schema(implementation = TokenResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Неверные данные"
    )
    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }

    @Operation(
            summary = "Регистрация клиента",
            description = "Создание нового аккаунта клиента"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Пользователь успешно зарегистрирован",
            content = @Content(schema = @Schema(implementation = TokenResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Неверные данные"
    )
    @PostMapping("/register/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse registerCustomer(@Valid @RequestBody RegisterRequest request) {
        return authService.registerCustomer(request);
    }

    @Operation(
            summary = "Регистрация администратора",
            description = "Создание нового аккаунта администратора"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Администратор успешно зарегистрирован",
            content = @Content(schema = @Schema(implementation = TokenResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Неверные данные"
    )
    @PostMapping("/register/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse registerAdmin(@Valid @RequestBody RegisterRequest request) {
        return authService.registerAdmin(request);
    }

    @Operation(
            summary = "Обновление токена",
            description = "Получение новой пары access/refresh токенов"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Токены успешно обновлены",
            content = @Content(schema = @Schema(implementation = TokenResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Недействительный refresh token"
    )
    @PostMapping("/refresh")
    public TokenResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}