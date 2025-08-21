package ru.kulbaka.bushunter.dto.user;

import ru.kulbaka.bushunter.security.model.UserRole;

public record UserResponse(
        Long id,
        String username,
        String fullName,
        UserRole role
) {}
