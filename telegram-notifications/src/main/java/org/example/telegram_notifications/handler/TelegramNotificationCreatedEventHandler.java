package org.example.telegram_notifications.handler;

import lombok.RequiredArgsConstructor;
import org.example.telegram_notifications.events.TelegramNotificationCreatedEvent;
import org.example.telegram_notifications.exception.NonRetryableException;
import org.example.telegram_notifications.exception.TelegramUserRejection;
import org.example.telegram_notifications.service.TelegramNotificationService;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramNotificationCreatedEventHandler {
    private final Logger LOGGER;
    private final TelegramNotificationService service;

    @KafkaListener(topics = "telegram-notification-created-topic", containerFactory = "kafkaListenerContainerCreatedFactory")
    public void handle(TelegramNotificationCreatedEvent event)  {
        LOGGER.info("Received event from " + event.getCallerUsername());

        try {
            service.sendMessage(event.getTelegramId(), event.getCallerUsername(), event.getMessage());
        } catch (TelegramApiException ex) {
            LOGGER.error("Non retryable exception occured, because of TelegramApiException. Message body: " + ex.getMessage());
            throw new NonRetryableException(ex);
        } catch (TelegramUserRejection ex) {
            LOGGER.error("Non retryable exception occured, because of TelegramUserRejection. Message body: " + ex.getMessage());
            throw new NonRetryableException(ex);
        }
    }
}
