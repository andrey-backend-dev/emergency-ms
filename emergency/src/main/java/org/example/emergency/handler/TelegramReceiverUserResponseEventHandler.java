package org.example.emergency.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.example.emergency.entity.Receiver;
import org.example.emergency.events.TelegramReceiverUserResponseEvent;
import org.example.emergency.exception.custom.NonRetryableException;
import org.example.emergency.repository.ReceiverRepository;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelegramReceiverUserResponseEventHandler {
    private final Logger LOGGER;
    private final ReceiverRepository repository;

    @KafkaListener(topics = "telegram-receiver-user-response-topic")
    @Transactional
    public void handle(TelegramReceiverUserResponseEvent event)  {
        LOGGER.info("Received event to receiver with id " + event.getReceiverId());

        try {
            Receiver receiver = repository.findById(event.getReceiverId()).orElseThrow(
                () -> new IllegalArgumentException("The receiver with id " + event.getReceiverId() + " does not exist.")
            );
            receiver.setTelegramId(event.getChatId());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Non retryable exception occured, because of IllegalArgumentException. Message body: " + ex.getMessage());
            throw new NonRetryableException(ex);
        }
    }
}
