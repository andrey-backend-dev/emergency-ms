package org.example.telegram_notifications.service.impls;

import jakarta.validation.ValidationException;
import org.example.telegram_notifications.dto.TelegramUserDTO;
import org.example.telegram_notifications.persistance.entity.TelegramUser;
import org.example.telegram_notifications.persistance.repository.TelegramUserRepository;
import org.example.telegram_notifications.service.TelegramUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.telegram_notifications.util.mappers.TelegramUserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class TelegramUserServiceImpl implements TelegramUserService {
    private final TelegramUserRepository repository;
    private final TelegramUserMapper mapper;
    @Value("${business.username-regex}")
    private String usernameRegex;

    @Override
    @Transactional
    public TelegramUser findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("The telegram user with username " + username + " does not exist.")
        );
    }

    @Override
    @Transactional
    public TelegramUserDTO create(long chatId, String username, boolean status) {

        TelegramUser user = repository.findById(chatId).orElse(null);

        if (user != null)
            return mapper.userToUserDto(user);

        TelegramUser entity = new TelegramUser(chatId, validateTelegramUsername(username), status);

        entity = repository.save(entity);

        return mapper.userToUserDto(entity);
    }

    @Override
    public TelegramUserDTO create(long chatId, String username) {
        return create(chatId, username, false);
    }

    @Override
    @Transactional
    public TelegramUserDTO changeStatus(long chatId, boolean status) {
        TelegramUser user = repository.findById(chatId).orElseThrow(
                () -> new IllegalArgumentException("The telegram user with chat id " + chatId + " does not exist.")
        );

        user.setStatus(status);

        return mapper.userToUserDto(user);
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
