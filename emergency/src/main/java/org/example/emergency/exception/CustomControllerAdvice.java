package org.example.emergency.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.concurrent.ExecutionException;

@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(value = {ValidationException.class, IllegalArgumentException.class})
    public ResponseEntity<?> badRequestHandler(RuntimeException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedHandler(AccessDeniedException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {ExecutionException.class, InterruptedException.class})
    public ResponseEntity<?> serverErrorHandler(Exception exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
