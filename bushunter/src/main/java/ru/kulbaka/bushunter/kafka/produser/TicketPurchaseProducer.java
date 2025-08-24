package ru.kulbaka.bushunter.kafka.produser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.kulbaka.bushunter.kafka.model.TicketPurchaseEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketPurchaseProducer {

    private final KafkaTemplate<String, TicketPurchaseEvent> kafkaTemplate;

    private static final String TOPIC = "ticket-purchases";

    public void sendTicketPurchaseEvent(TicketPurchaseEvent event) {
        try {
            kafkaTemplate.send(TOPIC, event);
            log.info("Отправлено сообщение о покупке билета в топик " + event);
        } catch (Exception e) {
            log.error("Ошибка отправки сообщения " + event + " в топик: " + e.getMessage());
        }
    }
}
