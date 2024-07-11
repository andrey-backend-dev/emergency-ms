package org.example.emergency.events;

public class TelegramNotificationCreatedEvent {
    private long telegramId;
    private String callerUsername;
    private String message;

    TelegramNotificationCreatedEvent() {
    }

    public TelegramNotificationCreatedEvent(long telegramId, String callerUsername, String message) {
        this.telegramId = telegramId;
        this.callerUsername = callerUsername;
        this.message = message;
    }

    public long getTelegramId() {
        return telegramId;
    }

    public String getCallerUsername() {
        return callerUsername;
    }

    public String getMessage() {
        return message;
    }
}
