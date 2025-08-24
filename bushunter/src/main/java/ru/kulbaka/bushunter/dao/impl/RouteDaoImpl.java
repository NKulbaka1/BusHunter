package ru.kulbaka.bushunter.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.kulbaka.bushunter.dao.RouteDao;
import ru.kulbaka.bushunter.mapper.RouteMapper;
import ru.kulbaka.bushunter.model.Route;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RouteDaoImpl implements RouteDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RouteMapper routeMapper;

    @Override
    public List<Route> findAll() {
        String sql = """
                SELECT
                    r.id as route_id,
                    r.departure_point,
                    r.destination_point,
                    r.duration_minutes,
                    r.carrier_id,
                    c.id as carrier_id,
                    c.name as carrier_name,
                    c.phone as carrier_phone
                  FROM routes r
                  JOIN carriers c ON r.carrier_id = c.id
                  ORDER BY r.departure_point
                """;
        return jdbcTemplate.query(sql, routeMapper::mapRow);
    }

    @Override
    public Optional<Route> findById(Long id) {
        String sql = """
                SELECT
                    r.id as route_id,
                    r.departure_point,
                    r.destination_point,
                    r.duration_minutes,
                    r.carrier_id,
                    c.id as carrier_id,
                    c.name as carrier_name,
                    c.phone as carrier_phone
                FROM routes r
                JOIN carriers c ON r.carrier_id = c.id
                WHERE r.id = :id
                """;
        try {
            Route route = jdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource("id", id),
                    routeMapper::mapRow);
            return Optional.ofNullable(route);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long create(Route route) {
        String sql = """
                INSERT INTO routes (departure_point, destination_point, duration_minutes, carrier_id) 
                VALUES (:departurePoint, :destinationPoint, :durationMinutes, :carrierId) 
                RETURNING id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("departurePoint", route.getDeparturePoint())
                .addValue("destinationPoint", route.getDestinationPoint())
                .addValue("durationMinutes", route.getDurationMinutes())
                .addValue("carrierId", route.getCarrier().getId());

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public void update(Route route) {
        String sql = """
                UPDATE routes
                SET departure_point = :departurePoint, 
                    destination_point = :destinationPoint, 
                    duration_minutes = :durationMinutes, 
                    carrier_id = :carrierId 
                WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", route.getId())
                .addValue("departurePoint", route.getDeparturePoint())
                .addValue("destinationPoint", route.getDestinationPoint())
                .addValue("durationMinutes", route.getDurationMinutes())
                .addValue("carrierId", route.getCarrier().getId());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM routes WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM routes WHERE id = :id";
        Integer count = jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id), Integer.class);
        return count > 0;
    }
}