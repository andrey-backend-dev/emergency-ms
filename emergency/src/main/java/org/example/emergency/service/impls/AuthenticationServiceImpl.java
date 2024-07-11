package org.example.emergency.service.impls;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.emergency.dto.request.CallerCreateDTO;
import org.example.emergency.dto.request.LoginDTO;
import org.example.emergency.dto.response.CallerResponseDTO;
import org.example.emergency.service.AuthenticationService;
import org.example.emergency.service.CallerService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import jakarta.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final CallerService callerService;
    private final AuthenticationManager manager;

    @Override
    public CallerResponseDTO register(CallerCreateDTO dto, HttpServletRequest request, HttpServletResponse response) throws ValidationException {
        CallerResponseDTO responseDTO = callerService.create(dto);
        authenticate(dto.getUsername(), dto.getPassword(), request, response);
        return responseDTO;
    }

    @Override
    public CallerResponseDTO login(LoginDTO dto, HttpServletRequest request, HttpServletResponse response) {
        authenticate(dto.getUsername(), dto.getPassword(), request, response);
        return callerService.findByUsername(dto.getUsername());
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        securityContextHolderStrategy.setContext(context);
        new HttpSessionSecurityContextRepository().saveContext(context, request, response);
    }

    @Override
    public void authenticate(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContext context = SecurityContextHolder.getContextHolderStrategy().createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.getContextHolderStrategy().setContext(context);
        new HttpSessionSecurityContextRepository().saveContext(context, request, response);
    }
}
