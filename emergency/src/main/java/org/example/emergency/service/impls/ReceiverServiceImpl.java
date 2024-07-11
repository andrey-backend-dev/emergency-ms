package org.example.emergency.service.impls;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.example.emergency.dto.request.ReceiverCreateDTO;
import org.example.emergency.dto.request.ReceiverUpdateDTO;
import org.example.emergency.dto.response.ReceiverDTO;
import org.example.emergency.entity.Caller;
import org.example.emergency.entity.Receiver;
import org.example.emergency.events.TelegramReceiverUserRequestEvent;
import org.example.emergency.repository.CallerRepository;
import org.example.emergency.repository.ReceiverRepository;
import org.example.emergency.service.ReceiverService;
import org.example.emergency.util.mappers.ReceiverMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ReceiverServiceImpl implements ReceiverService {
    private final ReceiverRepository repository;
    private final CallerRepository callerRepository;
    private final ReceiverMapper mapper;
    private final KafkaTemplate<String, TelegramReceiverUserRequestEvent> kafkaTemplate;
    private final Logger LOGGER;
    @Value("${kafka.topics.telegram-user}")
    private String telegramUserRequestTopic;
    @Value("${business.username-regex}")
    private String usernameRegex;

    @Override
    @Transactional
    public ReceiverDTO create(String username, ReceiverCreateDTO dto) throws ExecutionException, InterruptedException {

        Caller caller = callerRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("The user with username " + username + " does not exist.")
        );

        Receiver entity = new Receiver(
                caller,
                dto.getName(),
                dto.getEmail(),
                null);

        entity = repository.save(entity);

       kafkaTemplate.send(
                telegramUserRequestTopic,
                new TelegramReceiverUserRequestEvent(entity.getId(), validateTelegramUsername(dto.getTelegramUsername()))
        ).get();

        return mapper.receiverToReceiverDto(entity);
    }

    @Override
    public List<ReceiverDTO> findAllByCallerUsername(String username) {
        Caller caller = callerRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("The caller with username " + username + " does not exist.")
        );

        return repository.findByCallerId(caller.getId()).stream().map(mapper::receiverToReceiverDto).toList();
    }

    @Override
    @Transactional
    public ReceiverDTO updateByUsername(String username, ReceiverUpdateDTO dto) throws ExecutionException, InterruptedException {

        Caller caller = callerRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("The caller with username " + username + " does not exist.")
        );

        Receiver receiver = repository.findOneByCallerIdAndName(caller.getId(), dto.getName()).orElseThrow(
                () -> new IllegalArgumentException("No such receiver found with caller id " + caller.getId() + " and receiver name " + dto.getName() + ".")
        );

        SendResult<String, TelegramReceiverUserRequestEvent> result = kafkaTemplate.send(
                telegramUserRequestTopic,
                new TelegramReceiverUserRequestEvent(receiver.getId(), dto.getTelegramUsername())
        ).get();

        String name = dto.getName();
        if (name != null && !name.isBlank()) {
            receiver.setName(name);
        }

        String email = dto.getEmail();
        if (email != null && !email.isBlank()) {
            receiver.setEmail(email);
        }

        return mapper.receiverToReceiverDto(receiver);
    }

    @Override
    public String validateTelegramUsername(String telegramUsername) {
        if (telegramUsername == null)
            return null;
        if (Pattern.matches(usernameRegex, telegramUsername))
            return telegramUsername;
        throw new ValidationException("Telegram username is invalid");
    }
}
