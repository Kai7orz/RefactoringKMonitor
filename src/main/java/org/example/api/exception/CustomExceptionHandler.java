package org.example.api.exception;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({FieldErrorResource.class})
    public String handleInvalidField(RuntimeException e){
        FieldErrorResource fer = (FieldErrorResource) e;

        return "test";
    }

}
