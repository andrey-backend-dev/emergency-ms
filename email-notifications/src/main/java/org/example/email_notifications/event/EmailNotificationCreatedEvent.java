package org.example.email_notifications.event;

import lombok.Getter;

@Getter
public class EmailNotificationCreatedEvent {
    private String receiverEmail;
    private String receiverName;
    private String callerUsername;
    private String message;

    public EmailNotificationCreatedEvent() {
    }

    public EmailNotificationCreatedEvent(String receiverEmail, String receiverName, String callerUsername, String message) {
        this.receiverEmail = receiverEmail;
        this.receiverName = receiverName;
        this.callerUsername = callerUsername;
        this.message = message;
    }
}
