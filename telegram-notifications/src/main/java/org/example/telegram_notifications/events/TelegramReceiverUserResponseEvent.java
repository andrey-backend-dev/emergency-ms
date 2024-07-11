package org.example.telegram_notifications.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TelegramReceiverUserResponseEvent {
    private long receiverId;
    private long chatId;
}
