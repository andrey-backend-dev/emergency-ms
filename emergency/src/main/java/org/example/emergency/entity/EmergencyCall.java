package org.example.emergency.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.emergency.util.enums.ServiceEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Emergency_Call")
public class EmergencyCall {
    @Id
    @SequenceGenerator(name = "emergency_id_seq", sequenceName = "emergency_id_seq", allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emergency_id_seq")
    private long id;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Receiver receiver;
    @Enumerated(EnumType.STRING)
    private ServiceEnum service;
    private LocalDateTime dateCalled;
    private LocalDateTime dateReceived;

    public EmergencyCall(Receiver receiver, ServiceEnum service, LocalDateTime dateCalled) {
        this.receiver = receiver;
        this.service = service;
        this.dateCalled = dateCalled;
    }
}
