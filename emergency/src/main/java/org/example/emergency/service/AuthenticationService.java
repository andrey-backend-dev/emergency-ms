package org.example.emergency.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.emergency.dto.request.CallerCreateDTO;
import org.example.emergency.dto.request.LoginDTO;
import org.example.emergency.dto.response.CallerResponseDTO;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.ValidationException;

@Validated
public interface AuthenticationService {

    CallerResponseDTO register(@Valid CallerCreateDTO dto, HttpServletRequest request, HttpServletResponse response) throws ValidationException;

    CallerResponseDTO login(@Valid LoginDTO dto, HttpServletRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);

    void authenticate(String username, String password, HttpServletRequest request, HttpServletResponse response);
}
