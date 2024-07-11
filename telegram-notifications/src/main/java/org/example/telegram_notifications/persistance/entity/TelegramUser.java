package org.example.telegram_notifications.persistance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Telegram_User")
@Getter
@Setter
@NoArgsConstructor
public class TelegramUser {
    @Id
    private long chatId;
    private String username;
    private boolean status;

    public TelegramUser(long chatId, String username, boolean status) {
        this.chatId = chatId;
        this.username = username;
        this.status = status;
    }

    public TelegramUser(long chatId, String username) {
        this.chatId = chatId;
        this.username = username;
    }
}
