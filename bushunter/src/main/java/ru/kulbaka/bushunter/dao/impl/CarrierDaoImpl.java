package ru.kulbaka.bushunter.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kulbaka.bushunter.dao.CarrierDao;
import ru.kulbaka.bushunter.mapper.CarrierMapper;
import ru.kulbaka.bushunter.model.Carrier;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarrierDaoImpl implements CarrierDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Carrier> findAll() {
        String sql = "SELECT * FROM carriers ORDER BY name";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Carrier.class));
    }

    @Override
    public Optional<Carrier> findById(Long id) {
        String sql = "SELECT * FROM carriers WHERE id = :id";
        try {
            Carrier carrier = jdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource("id", id),
                    new BeanPropertyRowMapper<>(Carrier.class));
            return Optional.ofNullable(carrier);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long create(Carrier carrier) {
        String sql = """
                INSERT INTO carriers (name, phone)
                VALUES (:name, :phone)
                RETURNING id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", carrier.getName())
                .addValue("phone", carrier.getPhone());

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public void update(Carrier carrier) {
        String sql = """
                UPDATE carriers
                SET name = :name, phone = :phone
                WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", carrier.getId())
                .addValue("name", carrier.getName())
                .addValue("phone", carrier.getPhone());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM carriers WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM carriers WHERE id = :id";
        Integer count = jdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource("id", id),
                Integer.class
        );
        return count > 0;
    }
}
