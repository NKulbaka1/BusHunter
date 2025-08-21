package ru.kulbaka.bushunter.security.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {}
