package org.example.application;

import lombok.Getter;

@Getter
public class UserLoginParam {
    private String email;
    private String passwordRow;

    public UserLoginParam(String email, String passwordRow){
        this.email = email;
        this.passwordRow = passwordRow;
    }
}
