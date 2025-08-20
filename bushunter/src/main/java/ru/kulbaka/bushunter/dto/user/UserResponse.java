package ru.kulbaka.bushunter.dto.user;

public record UserResponse(
        Long id,
        String username,
        String fullName
) {}
