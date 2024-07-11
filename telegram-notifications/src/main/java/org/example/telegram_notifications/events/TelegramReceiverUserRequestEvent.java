package org.example.telegram_notifications.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TelegramReceiverUserRequestEvent {
    private long receiverId;
    private String telegramUsername;
}
