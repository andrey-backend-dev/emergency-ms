package org.example.emergency.controller;

import org.example.emergency.dto.response.EmergencyCallDTO;
import org.example.emergency.service.EmergencyCallService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/emergency-calls/")
public class EmergencyCallController {
    private final EmergencyCallService service;

    EmergencyCallController(EmergencyCallService service) {
        this.service = service;
    }

    @PostMapping(path = "call/", produces = MediaType.APPLICATION_JSON_VALUE)
    List<EmergencyCallDTO> makeEmergencyCalls(Principal principal) {
        return service.makeEmergencyCalls(principal.getName());
    }
}
