package ru.kulbaka.bushunter.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserDto(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String fullName
) {}
