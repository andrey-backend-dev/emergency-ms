package org.example.emergency.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmergencyCallDTO {
    private long id;
    private long receiverId;
    private LocalDateTime dateCalled;
    private LocalDateTime dateReceived;
}
