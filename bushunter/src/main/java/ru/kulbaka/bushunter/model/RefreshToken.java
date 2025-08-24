package ru.kulbaka.bushunter.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefreshToken {
    private Long id;
    private String token;
    private Long userId;
    private LocalDateTime expirationDate;
    private boolean revoked;
}
