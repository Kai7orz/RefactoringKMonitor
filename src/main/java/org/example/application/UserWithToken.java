package org.example.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.example.core.user.User;

@Getter
public class UserWithToken {
    private final User user;
    private final String token;

    public UserWithToken(@Valid User user, @Valid String token) {
        this.user = user;
        this.token = token;
    }
}
