package org.example.emergency.service;

import jakarta.validation.constraints.NotBlank;
import org.example.emergency.dto.response.EmergencyCallDTO;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface EmergencyCallService {
    List<EmergencyCallDTO> makeEmergencyCalls(@NotBlank(message = "Username is mandatory.") String username);
}
