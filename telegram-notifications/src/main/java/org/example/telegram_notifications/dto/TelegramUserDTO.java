package org.example.telegram_notifications.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TelegramUserDTO {
    private String username;
    private boolean status;
}
