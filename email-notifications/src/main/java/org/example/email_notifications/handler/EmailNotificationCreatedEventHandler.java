package org.example.email_notifications.handler;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.email_notifications.event.EmailNotificationCreatedEvent;
import org.example.email_notifications.exception.NonRetryableException;
import org.example.email_notifications.service.EmailNotificationService;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "email-notification-created-topic")
@RequiredArgsConstructor
public class EmailNotificationCreatedEventHandler {
    private final EmailNotificationService service;
    private final Logger LOGGER;

    @KafkaHandler
    public void handle(EmailNotificationCreatedEvent event) {
        LOGGER.info("Received event from " + event.getCallerUsername());

        try {
            service.sendMessage(event.getReceiverEmail(), event.getMessage(), event.getReceiverName(), event.getCallerUsername());
        } catch (MessagingException ex) {
            throw new NonRetryableException(ex);
        }
    }
}
