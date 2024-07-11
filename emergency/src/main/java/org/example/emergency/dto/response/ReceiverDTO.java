package org.example.emergency.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReceiverDTO {
    private long callerId;
    private String name;
    private String email;
    private String telegramId;
}
