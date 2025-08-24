package ru.kulbaka.bushunter.security.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import ru.kulbaka.bushunter.security.dao.RefreshTokenDao;

@Repository
@RequiredArgsConstructor
public class RefreshTokenDaoImpl implements RefreshTokenDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void saveRefreshToken(String token, Long userId) {
        String sql = """
            INSERT INTO refresh_tokens (token, user_id, expiration_date, revoked) 
            VALUES (:token, :userId, NOW() + INTERVAL '7 days', false)
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("token", token)
                .addValue("userId", userId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public boolean isTokenRevoked(String token) {
        String sql = "SELECT revoked FROM refresh_tokens WHERE token = :token";
        try {
            return jdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource("token", token), Boolean.class);
        } catch (EmptyResultDataAccessException e) {
            return true; // Токена нет в БД = отозван
        }
    }

    @Override
    public void revokeToken(String token) {
        String sql = "UPDATE refresh_tokens SET revoked = true WHERE token = :token";
        jdbcTemplate.update(sql, new MapSqlParameterSource("token", token));
    }

    @Override
    @Scheduled(fixedRate = 86400000)
    public void cleanExpiredTokens() {
        String sql = "DELETE FROM refresh_tokens WHERE expiration_date < NOW() OR revoked = true";
        jdbcTemplate.update(sql, new MapSqlParameterSource());
    }
}