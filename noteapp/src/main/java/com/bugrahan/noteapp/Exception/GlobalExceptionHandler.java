package com.bugrahan.noteapp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Tüm controller'larda oluşan validasyon ve diğer hataları tek yerden yönetir.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationHatalari(MethodArgumentNotValidException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse("Validasyon hatası");

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                response.addError(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
