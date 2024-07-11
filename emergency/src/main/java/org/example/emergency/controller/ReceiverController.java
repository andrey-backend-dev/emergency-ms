package org.example.emergency.controller;

import lombok.RequiredArgsConstructor;
import org.example.emergency.dto.request.ReceiverCreateDTO;
import org.example.emergency.dto.request.ReceiverUpdateDTO;
import org.example.emergency.dto.response.ReceiverDTO;
import org.example.emergency.service.ReceiverService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/receivers/")
@RequiredArgsConstructor
public class ReceiverController {
    private final ReceiverService service;

    @PostMapping(path = "create/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReceiverDTO create(Principal principal, @RequestBody ReceiverCreateDTO dto) throws ExecutionException, InterruptedException {
        return service.create(principal.getName(), dto);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReceiverDTO> findAllByCallerUsername(Principal principal) {
        return service.findAllByCallerUsername(principal.getName());
    }

    @PatchMapping(path = "update/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReceiverDTO updateByUsername(Principal principal, @RequestBody ReceiverUpdateDTO dto) throws AccessDeniedException, ExecutionException, InterruptedException {
        return service.updateByUsername(principal.getName(), dto);
    }
}
