package org.example.emergency.exception.custom;

public class RetryableException extends RuntimeException {

    public RetryableException(String msg) {
        super(msg);
    }

    public RetryableException(Throwable cause) {
        super(cause);
    }
}
