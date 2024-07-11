package org.example.emergency.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Receiver {
    @Id
    @SequenceGenerator(name = "receiver_id_seq", sequenceName = "receiver_id_seq", allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receiver_id_seq")
    private long id;
    @ManyToOne
    @JoinColumn(name = "caller_id")
    private Caller caller;
    private String name;
    private String email;
    @JoinColumn(name = "telegram_id")
    private Long telegramId;

    public Receiver(Caller caller, String name, String email, Long telegramId) {
        this.caller = caller;
        this.name = name;
        this.email = email;
        this.telegramId = telegramId;
    }
}
