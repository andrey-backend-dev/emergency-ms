package org.example.emergency.service.impls;

import lombok.RequiredArgsConstructor;
import org.example.emergency.dto.response.EmergencyCallDTO;
import org.example.emergency.entity.Caller;
import org.example.emergency.entity.EmergencyCall;
import org.example.emergency.entity.Receiver;
import org.example.emergency.events.EmailNotificationCreatedEvent;
import org.example.emergency.events.TelegramNotificationCreatedEvent;
import org.example.emergency.repository.CallerRepository;
import org.example.emergency.repository.EmergencyCallRepository;
import org.example.emergency.service.EmergencyCallService;
import org.example.emergency.util.enums.ServiceEnum;
import org.example.emergency.util.mappers.EmergencyCallMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class EmergencyCallServiceImpl implements EmergencyCallService {
    private final EmergencyCallRepository repository;
    private final CallerRepository callerRepository;
    private final EmergencyCallMapper mapper;
    private final KafkaTemplate<String, EmailNotificationCreatedEvent> emailKafkaTemplate;
    private final KafkaTemplate<String, TelegramNotificationCreatedEvent> telegramKafkaTemplate;
    private final Logger LOGGER;

    @Value("${kafka.topics.email}")
    private String emailTopic;
    @Value("${kafka.topics.telegram}")
    private String telegramTopic;

    @Override
    public List<EmergencyCallDTO> makeEmergencyCalls(String username) {
        Caller caller = callerRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("The user with username " + username + " does not exist.")
        );

        Set<Receiver> receivers = caller.getReceivers();
        List<EmergencyCallDTO> emergencyCalls = new ArrayList<>();

        for (Receiver receiver : receivers) {
            if (receiver.getEmail() != null) {
                EmergencyCall emergencyCall = makeEmailEmergencyCall(receiver, caller);
                emergencyCalls.add(mapper.callToCallDto(emergencyCall));
            }

            if (receiver.getTelegramId() != null) {
                EmergencyCall emergencyCall = makeTelegramEmergencyCall(receiver, caller);
                emergencyCalls.add(mapper.callToCallDto(emergencyCall));
            }
        }

        return emergencyCalls;
    }

    private EmergencyCall makeEmailEmergencyCall(Receiver receiver, Caller caller) {
        EmergencyCall emergencyCall = new EmergencyCall(receiver, ServiceEnum.EMAIL, LocalDateTime.now());

        CompletableFuture<SendResult<String, EmailNotificationCreatedEvent>> future = emailKafkaTemplate.send(
                emailTopic,
                new EmailNotificationCreatedEvent(
                        receiver.getEmail(),
                        receiver.getName(),
                        caller.getUsername(),
                        caller.getMessage()
                )
        );

        future.whenComplete(
                (result, exc) -> {
                    if (exc != null) {
                        LOGGER.error("Failed to send message: " + exc.getMessage());
                    } else {
                        emergencyCall.setDateReceived(LocalDateTime.now());
                        LOGGER.info("Message send successfully: " + result.getRecordMetadata());
                    }
                }
        );

        return repository.save(emergencyCall);
    }

    private EmergencyCall makeTelegramEmergencyCall(Receiver receiver, Caller caller) {
        EmergencyCall emergencyCall = new EmergencyCall(receiver, ServiceEnum.TELEGRAM, LocalDateTime.now());

        CompletableFuture<SendResult<String, TelegramNotificationCreatedEvent>> future = telegramKafkaTemplate.send(
                telegramTopic,
                new TelegramNotificationCreatedEvent(
                        receiver.getTelegramId(),
                        caller.getUsername(),
                        caller.getMessage()
                )
        );

        future.whenComplete(
                (result, exc) -> {
                    if (exc != null) {
                        LOGGER.error("Failed to send message: " + exc.getMessage());
                    } else {
                        emergencyCall.setDateReceived(LocalDateTime.now());
                        LOGGER.info("Message send successfully: " + result.getRecordMetadata());
                    }
                }
        );

        return repository.save(emergencyCall);
    }
}
