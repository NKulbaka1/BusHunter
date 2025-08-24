package ru.kulbaka.bushunter.security.service;

import ru.kulbaka.bushunter.security.dto.AuthRequest;
import ru.kulbaka.bushunter.security.dto.RefreshTokenRequest;
import ru.kulbaka.bushunter.security.dto.RegisterRequest;
import ru.kulbaka.bushunter.security.dto.TokenResponse;

public interface AuthService {
    TokenResponse authenticate(AuthRequest authRequest);
    TokenResponse registerCustomer(RegisterRequest registerRequest);
    TokenResponse registerAdmin(RegisterRequest registerRequest);
    TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
