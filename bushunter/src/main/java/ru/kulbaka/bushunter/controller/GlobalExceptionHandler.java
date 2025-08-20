package ru.kulbaka.bushunter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kulbaka.bushunter.exception.EntityNotFoundException;
import ru.kulbaka.bushunter.exception.TicketAlreadyPurchasedException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(TicketAlreadyPurchasedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTicketAlreadyPurchasedException(TicketAlreadyPurchasedException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }
}
