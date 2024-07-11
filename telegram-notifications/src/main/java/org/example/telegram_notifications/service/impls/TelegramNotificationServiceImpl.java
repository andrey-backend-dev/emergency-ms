package org.example.telegram_notifications.service.impls;

import lombok.RequiredArgsConstructor;
import org.example.telegram_notifications.exception.TelegramUserRejection;
import org.example.telegram_notifications.persistance.entity.TelegramUser;
import org.example.telegram_notifications.persistance.repository.TelegramUserRepository;
import org.example.telegram_notifications.service.TelegramNotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TelegramNotificationServiceImpl implements TelegramNotificationService {
    private final TelegramBot bot;
    private final TelegramUserRepository repository;
    @Value("${business.message}")
    private String templateMessage;


    public LocalDateTime sendMessage(long telegramId, String callerUsername, String message) throws TelegramApiException, TelegramUserRejection {
        TelegramUser user = repository.findById(telegramId).orElseThrow(
                () -> new IllegalArgumentException("The telegram user with id " + telegramId + " does not exist.")
        );

        if (user.isStatus()) {
            bot.execute(new SendMessage(
                    String.valueOf(telegramId),
                    generateMessage(
                            user.getUsername(),
                            callerUsername,
                            message
                    )
            ));
        } else {
            throw new TelegramUserRejection("User with chatId '" + user.getChatId() + "' rejected notifications.");
        }

        return LocalDateTime.now();
    }

    public String generateMessage(String receiver, String callerName, String message) {

        String resultMsg = String.format(templateMessage, receiver, callerName);

        if (message != null && !message.isBlank())
            resultMsg = resultMsg + "\n\nСообщение пользователя: " + message;

        return resultMsg;
    }
}
