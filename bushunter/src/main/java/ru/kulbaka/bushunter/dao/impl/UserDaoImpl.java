package ru.kulbaka.bushunter.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.kulbaka.bushunter.dao.UserDao;
import ru.kulbaka.bushunter.model.User;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = :username";
        try {
            User user = jdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource("username", username),
                    new BeanPropertyRowMapper<>(User.class));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long create(User user) {
        String sql = """
            INSERT INTO users (username, password, full_name, role) 
            VALUES (:username, :password, :fullName, :role) 
            RETURNING id
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword())
                .addValue("fullName", user.getFullName())
                .addValue("role", user.getRole().name());

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = :username";
        Integer count = jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("username", username), Integer.class);
        return count > 0;
    }
}
