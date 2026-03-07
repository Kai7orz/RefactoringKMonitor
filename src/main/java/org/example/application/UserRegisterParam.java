package org.example.application;

import lombok.Getter;

@Getter
public class UserRegisterParam {
    private String name;
    private String email;
    private String passwordRow;

    public UserRegisterParam(String name,String email,String passwordRow){
        this.name = name;
        this.email = email;
        this.passwordRow = passwordRow;
    }
}
