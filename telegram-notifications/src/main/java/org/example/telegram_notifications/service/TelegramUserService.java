package org.example.telegram_notifications.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.example.telegram_notifications.dto.TelegramUserDTO;
import org.example.telegram_notifications.persistance.entity.TelegramUser;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TelegramUserService {
    TelegramUser findByUsername(@NotBlank(message = "Username is mandatory.") String username);

    TelegramUserDTO create(@Positive(message = "Chat id must be positive.") long chatId,
                           @NotBlank(message = "Username is mandatory.") String username,
                           boolean status);

    TelegramUserDTO create(@Positive(message = "Chat id must be positive.") long chatId,
                           @NotBlank(message = "Username is mandatory.") String username);

    TelegramUserDTO changeStatus(@Positive(message = "Chat id must be positive.") long chatId,
                                 boolean status);

    String validateTelegramUsername(String telegramUsername);
}
