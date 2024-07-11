package org.example.telegram_notifications.service;

import org.example.telegram_notifications.exception.TelegramUserRejection;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

public interface TelegramNotificationService {

    LocalDateTime sendMessage(long telegramId, String callerUsername, String message) throws TelegramApiException, TelegramUserRejection;

    String generateMessage(String receiver, String callerName, String message);
}
