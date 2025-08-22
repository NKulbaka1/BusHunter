package ru.kulbaka.bushunter_kafka_consumer.service;

import ru.kulbaka.bushunter_kafka_consumer.model.TicketPurchaseEvent;

public interface TicketPurchaseService {
    void processTicketPurchase(TicketPurchaseEvent event);
}
