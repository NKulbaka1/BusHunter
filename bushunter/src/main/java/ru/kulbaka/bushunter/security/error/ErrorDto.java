package ru.kulbaka.bushunter.security.error;

public record ErrorDto(
        Integer responseStatus,
        String message
) {
}
