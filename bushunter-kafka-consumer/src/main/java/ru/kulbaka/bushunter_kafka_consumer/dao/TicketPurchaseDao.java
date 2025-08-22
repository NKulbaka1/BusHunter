package ru.kulbaka.bushunter_kafka_consumer.dao;

import ru.kulbaka.bushunter_kafka_consumer.model.PurchasedTicket;

public interface TicketPurchaseDao {
    void create(PurchasedTicket purchasedTicket);
}
