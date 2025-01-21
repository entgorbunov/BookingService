package com.pavels.web;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        log.error("Get common exception", exception);
        ErrorMessageResponse messageResponse = new ErrorMessageResponse("Internal error",
            exception.getMessage(),
            LocalDateTime.now());
        return ResponseEntity.status(500).body(messageResponse);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(Exception ex) {
        log.error("Get entity exception", ex);
        ErrorMessageResponse messageResponse = new ErrorMessageResponse(
            "Entity not found",
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(404).body(messageResponse);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleIllegalArgument(Exception ex) {
        log.error("Get bad request", ex);
        ErrorMessageResponse messageResponse = new ErrorMessageResponse(
            "Bad request",
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(400).body(messageResponse);
    }
}
