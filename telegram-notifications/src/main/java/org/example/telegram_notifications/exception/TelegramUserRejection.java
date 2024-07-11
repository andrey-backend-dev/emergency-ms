package org.example.telegram_notifications.exception;

public class TelegramUserRejection extends RuntimeException {

    public TelegramUserRejection(String msg) {
        super(msg);
    }
}
