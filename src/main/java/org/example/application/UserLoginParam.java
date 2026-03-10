package org.example.application;

import lombok.Getter;

@Getter
public class UserLoginParam {
    private final String email;
    private final String passwordRow;

    public UserLoginParam(String email, String passwordRow){
        this.email = email;
        this.passwordRow = passwordRow;
    }
}
