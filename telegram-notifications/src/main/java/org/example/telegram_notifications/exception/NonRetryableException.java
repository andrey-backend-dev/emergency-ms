package org.example.telegram_notifications.exception;

public class NonRetryableException extends RuntimeException {

    public NonRetryableException(String msg) {
        super(msg);
    }

    public NonRetryableException(Throwable cause) {
        super(cause);
    }
}
