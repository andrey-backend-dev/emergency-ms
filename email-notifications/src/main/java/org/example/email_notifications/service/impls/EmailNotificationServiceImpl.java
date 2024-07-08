package org.example.email_notifications.service.impls;

import jakarta.mail.MessagingException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.example.email_notifications.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService {
    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${business.mail-subject}")
    private String mailSubject;
    @Value("${business.message}")
    private String templateMessage;
    @Value("${business.email-regex}")
    private String emailRegex;

    public LocalDateTime sendMessage(String address, String message, String receiver, String callerName) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(emailSender.createMimeMessage(), true, "UTF-8");
        helper.setFrom(from);
        helper.setTo(address);
        helper.setSubject(mailSubject + " " + callerName);
        helper.setText(generateMessage(receiver, callerName, message), true);
        Date sentDate = Date.from(Instant.now());
        helper.setSentDate(sentDate);
        emailSender.send(helper.getMimeMessage());
        return sentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public String generateMessage(String receiver, String callerName, String message) {

        String resultMsg = String.format(templateMessage, receiver, callerName);

        if (message != null && !message.isBlank())
            resultMsg = resultMsg + "<br><br>Сообщение пользователя: " + message;

        return resultMsg;
    }

    @Override
    public String validateEmailFormat(String email) throws ValidationException {
        if (email == null)
            return null;
        if (Pattern.matches(emailRegex, email))
            return email;
        throw new ValidationException("Email is invalid");
    }
}
