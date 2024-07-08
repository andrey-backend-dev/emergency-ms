package org.example.email_notifications.service;

import jakarta.mail.MessagingException;
import jakarta.validation.ValidationException;

import java.time.LocalDateTime;

public interface EmailNotificationService {

    LocalDateTime sendMessage(String address, String message, String receiver, String callerName) throws MessagingException;

    String generateMessage(String receiver, String callerName, String message);

    String validateEmailFormat(String email) throws ValidationException;
}
