package ru.kulbaka.bushunter_kafka_consumer.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.kulbaka.bushunter_kafka_consumer.dao.TicketPurchaseDao;
import ru.kulbaka.bushunter_kafka_consumer.model.PurchasedTicket;

import java.sql.Types;

@Repository
@RequiredArgsConstructor
public class TicketPurchaseDaoImpl implements TicketPurchaseDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void create(PurchasedTicket purchasedTicket) {
        String sql = """
            INSERT INTO purchased_tickets (
                original_ticket_id, 
                seat_number, 
                price, 
                departure_date_time, 
                user_id, 
                route_id, 
                carrier_id
            ) VALUES (
                :originalTicketId, 
                :seatNumber, 
                :price, 
                :departureDateTime, 
                :userId, 
                :routeId, 
                :carrierId
            )
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("originalTicketId", purchasedTicket.getOriginalTicketId(), Types.BIGINT)
                .addValue("seatNumber", purchasedTicket.getSeatNumber(), Types.VARCHAR)
                .addValue("price", purchasedTicket.getPrice(), Types.DECIMAL)
                .addValue("departureDateTime", purchasedTicket.getDepartureDateTime(), Types.TIMESTAMP)
                .addValue("userId", purchasedTicket.getUserId(), Types.BIGINT)
                .addValue("routeId", purchasedTicket.getRouteId(), Types.BIGINT)
                .addValue("carrierId", purchasedTicket.getCarrierId(), Types.BIGINT);

        jdbcTemplate.update(sql, params);
    }
}
