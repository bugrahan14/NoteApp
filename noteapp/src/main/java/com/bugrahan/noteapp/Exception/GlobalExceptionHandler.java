package com.bugrahan.noteapp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationHatalari(MethodArgumentNotValidException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse("Validasyon hatası");

     

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->{
            // Console çıktısı
            System.out.println("Hatalı field: " + fieldError.getField());
            System.out.println("Hata mesajı: " + fieldError.getDefaultMessage());
            
                response.addError(fieldError.getField(), fieldError.getDefaultMessage());
            });


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
