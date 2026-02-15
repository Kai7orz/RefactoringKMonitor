package org.example.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyRegisterException extends RuntimeException{
    public AlreadyRegisterException(String message) {
        super(message);
    }
}
