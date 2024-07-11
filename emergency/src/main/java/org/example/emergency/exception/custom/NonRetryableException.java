package org.example.emergency.exception.custom;

public class NonRetryableException extends RuntimeException {

    public NonRetryableException(String msg) {
        super(msg);
    }

    public NonRetryableException(Throwable cause) {
        super(cause);
    }
}
