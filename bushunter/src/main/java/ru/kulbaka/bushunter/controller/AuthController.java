package ru.kulbaka.bushunter.controller;

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
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/register/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse registerCustomer(@Valid @RequestBody RegisterRequest request) {
        return authService.registerCustomer(request);
    }

    @PostMapping("/register/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse registerAdmin(@Valid @RequestBody RegisterRequest request) {
        return authService.registerAdmin(request);
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}