package ru.kulbaka.bushunter.dao.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.kulbaka.bushunter.dao.TicketDao;
import ru.kulbaka.bushunter.mapper.TicketMapper;
import ru.kulbaka.bushunter.model.Ticket;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketDaoImpl implements TicketDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final TicketMapper ticketMapper;

    public List<Ticket> findAvailableTickets(LocalDateTime dateFrom, LocalDateTime dateTo,
                                             String departurePoint, String destinationPoint,
                                             String carrierName, int page, int size) {
        String sql = """
                SELECT
                    t.id AS ticket_id,
                    t.seat_number,
                    t.price,
                    t.departure_date_time,
                    t.user_id,
                    t.route_id,
                    r.id AS route_id,
                    r.departure_point,
                    r.destination_point,
                    r.duration_minutes,
                    r.carrier_id,
                    c.id AS carrier_id,
                    c.name AS carrier_name,
                    c.phone AS carrier_phone
                FROM tickets t
                JOIN routes r ON t.route_id = r.id
                JOIN carriers c ON r.carrier_id = c.id
                WHERE t.user_id IS NULL
                AND (t.departure_date_time >= COALESCE(:dateFrom, TIMESTAMP '1900-01-01'))
                AND (t.departure_date_time <= COALESCE(:dateTo, TIMESTAMP '2100-01-01'))
                AND (r.departure_point ILIKE '%' || COALESCE(:departurePoint, '') || '%')
                AND (r.destination_point ILIKE '%' || COALESCE(:destinationPoint, '') || '%')
                AND (c.name ILIKE '%' || COALESCE(:carrierName, '') || '%')
                LIMIT :limit OFFSET :offset
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("dateFrom", dateFrom, Types.TIMESTAMP)
                .addValue("dateTo", dateTo, Types.TIMESTAMP)
                .addValue("departurePoint", departurePoint, Types.VARCHAR)
                .addValue("destinationPoint", destinationPoint, Types.VARCHAR)
                .addValue("carrierName", carrierName, Types.VARCHAR)
                .addValue("limit", size, Types.INTEGER)
                .addValue("offset", page * size, Types.INTEGER);

        return jdbcTemplate.query(sql, params, ticketMapper::mapRow);
    }

    //@Override
    public List<Ticket> findByUserId(Long userId) {
        String sql = """
                SELECT
                    t.id AS ticket_id,
                    t.seat_number,
                    t.price,
                    t.departure_date_time,
                    t.user_id,
                    t.route_id,
                    r.id AS route_id,
                    r.departure_point,
                    r.destination_point,
                    r.duration_minutes,
                    r.carrier_id,
                    c.id AS carrier_id,
                    c.name AS carrier_name,
                    c.phone AS carrier_phone
                FROM tickets t
                JOIN routes r ON t.route_id = r.id
                JOIN carriers c ON r.carrier_id = c.id
                WHERE t.user_id = :userId
                ORDER BY t.departure_date_time
                """;

        return jdbcTemplate.query(sql,
                new MapSqlParameterSource("userId", userId),
                ticketMapper::mapRow);
    }

    public boolean checkTicketPurchased(Long id) {
        String sql = """
                SELECT COUNT(*) FROM tickets
                WHERE id = :id AND user_id IS NULL
                """;
        Integer count = jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id), Integer.class);
        return !(count > 0);
    }

    public void purchaseTicket(Long ticketId, Long userId) {
        String sql = """
                UPDATE tickets SET user_id = :userId
                WHERE id = :ticketId
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("ticketId", ticketId)
                .addValue("userId", userId);

        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<Ticket> findAll() {
        String sql = """
                SELECT
                    t.id AS ticket_id,
                    t.seat_number,
                    t.price,
                    t.departure_date_time,
                    t.user_id,
                    t.route_id,
                    r.id AS route_id,
                    r.departure_point,
                    r.destination_point,
                    r.duration_minutes,
                    r.carrier_id,
                    c.id AS carrier_id,
                    c.name AS carrier_name,
                    c.phone AS carrier_phone
                FROM tickets t
                JOIN routes r ON t.route_id = r.id
                JOIN carriers c ON r.carrier_id = c.id
                ORDER BY t.departure_date_time
                """;
        return jdbcTemplate.query(sql, ticketMapper::mapRow);
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        String sql = """
                SELECT
                    t.id AS ticket_id,
                    t.seat_number,
                    t.price,
                    t.departure_date_time,
                    t.user_id,
                    t.route_id,
                    r.id AS route_id,
                    r.departure_point,
                    r.destination_point,
                    r.duration_minutes,
                    r.carrier_id,
                    c.id AS carrier_id,
                    c.name AS carrier_name,
                    c.phone AS carrier_phone
                FROM tickets t
                JOIN routes r ON t.route_id = r.id
                JOIN carriers c ON r.carrier_id = c.id
                WHERE t.id = :id
                """;
        try {
            Ticket ticket = jdbcTemplate.queryForObject(sql,
                    new MapSqlParameterSource("id", id),
                    ticketMapper::mapRow);
            return Optional.ofNullable(ticket);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long create(Ticket ticket) {
        String sql = """
                INSERT INTO tickets (seat_number, price, departure_date_time, route_id, user_id)
                VALUES (:seatNumber, :price, :departureDateTime, :routeId, :userId)
                RETURNING id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("seatNumber", ticket.getSeatNumber())
                .addValue("price", ticket.getPrice())
                .addValue("departureDateTime", ticket.getDepartureDateTime())
                .addValue("routeId", ticket.getRoute().getId())
                .addValue("userId", ticket.getUserId());

        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    @Override
    public void update(Ticket ticket) {
        String sql = """
                UPDATE tickets
                SET seat_number = :seatNumber,
                    price = :price,
                    departure_date_time = :departureDateTime,
                    route_id = :routeId,
                    user_id = :userId
                WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", ticket.getId())
                .addValue("seatNumber", ticket.getSeatNumber())
                .addValue("price", ticket.getPrice())
                .addValue("departureDateTime", ticket.getDepartureDateTime())
                .addValue("routeId", ticket.getRoute().getId())
                .addValue("userId", ticket.getUserId());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM tickets WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM tickets WHERE id = :id";
        Integer count = jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id), Integer.class);
        return count > 0;
    }
}
