package org.example.application;

import lombok.Getter;
import org.example.core.user.User;

@Getter
public class UserWithToken {
    private User user;
    private String token;

    public UserWithToken(User user,String token) {
        this.user = user;
        this.token = token;
    }
}
