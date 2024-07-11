package org.example.telegram_notifications.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TelegramNotificationCreatedEvent {
    private long telegramId;
    private String callerUsername;
    private String message;
}
