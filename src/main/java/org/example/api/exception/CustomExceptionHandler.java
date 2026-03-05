package org.example.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({FieldErrorResource.class})
    public ResponseEntity<String> handleInvalidField(RuntimeException e){
        FieldErrorResource fer = (FieldErrorResource) e;

        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthError(RuntimeException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }
}
