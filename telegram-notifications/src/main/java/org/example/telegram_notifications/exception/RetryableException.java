package org.example.telegram_notifications.exception;

public class RetryableException extends RuntimeException {

    public RetryableException(String msg) {
        super(msg);
    }

    public RetryableException(Throwable cause) {
        super(cause);
    }
}
