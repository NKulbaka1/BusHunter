package ru.kulbaka.bushunter_kafka_consumer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kulbaka.bushunter_kafka_consumer.dao.TicketPurchaseDao;
import ru.kulbaka.bushunter_kafka_consumer.model.PurchasedTicket;
import ru.kulbaka.bushunter_kafka_consumer.model.TicketPurchaseEvent;
import ru.kulbaka.bushunter_kafka_consumer.service.TicketPurchaseService;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketPurchaseServiceImpl implements TicketPurchaseService {
    private final TicketPurchaseDao dao;

    @Override
    @Transactional
    public void processTicketPurchase(TicketPurchaseEvent event) {
        try {
            PurchasedTicket purchasedTicket = convertToEntity(event);
            dao.create(purchasedTicket);

            log.info("Данные о билете с айди " + purchasedTicket.getOriginalTicketId() + " успешно сохранены");
        } catch (Exception e) {
            log.error("Ошибка обработки сообщения " + e.getMessage());
            throw e;
        }
    }

    private PurchasedTicket convertToEntity(TicketPurchaseEvent event) {
        PurchasedTicket ticket = new PurchasedTicket();
        ticket.setOriginalTicketId(event.getOriginalTicketId());
        ticket.setUserId(event.getUserId());
        ticket.setSeatNumber(event.getSeatNumber());
        ticket.setPrice(event.getPrice());
        ticket.setDepartureDateTime(event.getDepartureDateTime());
        ticket.setRouteId(event.getRouteId());
        ticket.setCarrierId(event.getCarrierId());

        return ticket;
    }
}
