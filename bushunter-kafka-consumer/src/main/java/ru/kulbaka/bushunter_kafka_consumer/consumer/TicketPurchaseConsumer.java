package ru.kulbaka.bushunter_kafka_consumer.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.kulbaka.bushunter_kafka_consumer.model.TicketPurchaseEvent;
import ru.kulbaka.bushunter_kafka_consumer.service.TicketPurchaseService;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketPurchaseConsumer {

    private final TicketPurchaseService ticketPurchaseService;

    @KafkaListener(
            topics = "${kafka.topic.ticket-purchases}",
            groupId = "${kafka.consumer-group}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(TicketPurchaseEvent event) {
        try {
            log.info("Получено сообщение " + event);
            ticketPurchaseService.processTicketPurchase(event);
        } catch (Exception e) {
            log.error("Ошибка обработки сообщения " + e.getMessage());
        }
    }
}
