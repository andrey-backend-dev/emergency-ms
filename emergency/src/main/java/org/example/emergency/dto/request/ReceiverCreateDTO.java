package org.example.emergency.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReceiverCreateDTO {
    @NotBlank(message = "Receiver name is mandatory.")
    private String name;
    private String email;
    private String telegramUsername;
}