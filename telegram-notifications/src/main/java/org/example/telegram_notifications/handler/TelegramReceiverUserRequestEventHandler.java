package org.example.telegram_notifications.handler;

import lombok.RequiredArgsConstructor;
import org.example.telegram_notifications.events.TelegramReceiverUserRequestEvent;
import org.example.telegram_notifications.events.TelegramReceiverUserResponseEvent;
import org.example.telegram_notifications.exception.NonRetryableException;
import org.example.telegram_notifications.persistance.entity.TelegramUser;
import org.example.telegram_notifications.service.TelegramUserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelegramReceiverUserRequestEventHandler {
    private final Logger LOGGER;
    private final TelegramUserService service;
    private final KafkaTemplate<String, TelegramReceiverUserResponseEvent> kafkaTemplate;
    @Value("${kafka.topics.telegram-user}")
    private String telegramReceiverResponseTopic;

    @KafkaListener(topics = "telegram-receiver-user-request-topic", containerFactory = "kafkaListenerContainerUserFactory")
    public void handle(TelegramReceiverUserRequestEvent event)  {
        LOGGER.info("Received event from receiver with id " + event.getReceiverId());

        try {
            TelegramUser user = service.findByUsername(event.getTelegramUsername());
            kafkaTemplate.send(
                    telegramReceiverResponseTopic,
                    new TelegramReceiverUserResponseEvent(event.getReceiverId(), user.getChatId())
            );
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Non retryable exception occured, because of IllegalArgumentException. Message body: " + ex.getMessage());
            throw new NonRetryableException(ex);
        }
    }
}
