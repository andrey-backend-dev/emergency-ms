package org.example.emergency.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class CallerUpdateDTO {
    private String password;
    private String email;
    private String message;
    private String image;
}
