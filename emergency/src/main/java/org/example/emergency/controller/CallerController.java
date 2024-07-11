package org.example.emergency.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.emergency.dto.request.CallerCreateDTO;
import org.example.emergency.dto.request.CallerUpdateDTO;
import org.example.emergency.dto.response.CallerResponseDTO;
import org.example.emergency.service.AuthenticationService;
import org.example.emergency.service.CallerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.ValidationException;
import java.security.Principal;

@RestController
@RequestMapping("/callers/")
@RequiredArgsConstructor
public class CallerController {
    private final CallerService service;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "create/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CallerResponseDTO create(@RequestBody CallerCreateDTO dto) throws ValidationException {
        return service.create(dto);
    }

    @GetMapping(value = "id/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public CallerResponseDTO findById(@PathVariable("id") long id) {
        return service.findById(id);
    }

    @GetMapping(value = "{username}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public CallerResponseDTO findByUsername(@PathVariable("username") String username) {
        return service.findByUsername(username);
    }

    @GetMapping(value = "me/", produces = MediaType.APPLICATION_JSON_VALUE)
    public CallerResponseDTO findByPrincipal(Principal principal) {
        return service.findByUsername(principal.getName());
    }

    @PatchMapping(value = "{username}/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CallerResponseDTO updateByUsername(@PathVariable("username") String username, @RequestBody CallerUpdateDTO dto) {
        return service.updateByUsername(username, dto);
    }

    @PatchMapping(value = "me/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CallerResponseDTO updateByPrincipal(Principal principal, @RequestBody CallerUpdateDTO dto) {
        return service.updateByUsername(principal.getName(), dto);
    }

    @DeleteMapping(value = "me/")
    public ResponseEntity<?> deleteByPrincipal(Principal principal, HttpServletRequest request, HttpServletResponse response) {
        service.deleteByUsername(principal.getName());
        authenticationService.logout(request, response);
        return ResponseEntity.ok("Caller deleted.");
    }

    @DeleteMapping(value = "{username}/")
    public ResponseEntity<?> deleteByUsername(@PathVariable("username") String username) {
        service.deleteByUsername(username);
        return ResponseEntity.ok("Caller deleted.");
    }
}
