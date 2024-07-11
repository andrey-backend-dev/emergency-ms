package org.example.emergency.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class ReceiverUpdateDTO {
    @NotBlank(message = "Name is mandatory.")
    private String name;
    private String newName;
    private String email;
    private String telegramUsername;
}
