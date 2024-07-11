package org.example.emergency.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.example.emergency.dto.request.ReceiverCreateDTO;
import org.example.emergency.dto.request.ReceiverUpdateDTO;
import org.example.emergency.dto.response.ReceiverDTO;
import org.springframework.validation.annotation.Validated;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Validated
public interface ReceiverService {
    ReceiverDTO create(@NotBlank(message = "Username is mandatory.") String username, @Valid ReceiverCreateDTO dto) throws ExecutionException, InterruptedException;

    ReceiverDTO updateByUsername(@NotBlank(message = "Name is mandatory.") String username, @Valid ReceiverUpdateDTO dto) throws AccessDeniedException, ExecutionException, InterruptedException;

    List<ReceiverDTO> findAllByCallerUsername(@NotBlank(message = "Username is mandatory.") String username);

    String validateTelegramUsername(String telegramUsername);
}
