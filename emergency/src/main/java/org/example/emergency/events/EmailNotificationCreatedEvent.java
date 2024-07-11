package org.example.emergency.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailNotificationCreatedEvent {
    private String receiverEmail;
    private String receiverName;
    private String callerUsername;
    private String message;
}
