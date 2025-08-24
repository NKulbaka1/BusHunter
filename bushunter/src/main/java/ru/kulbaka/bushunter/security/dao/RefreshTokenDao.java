package ru.kulbaka.bushunter.security.dao;

public interface RefreshTokenDao {
    void saveRefreshToken(String token, Long userId);
    boolean isTokenRevoked(String token);
    void revokeToken(String token);
    void cleanExpiredTokens();
}
