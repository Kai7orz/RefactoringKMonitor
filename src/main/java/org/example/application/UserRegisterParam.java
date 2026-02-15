package org.example.application;

import lombok.Getter;

@Getter
public class UserRegisterParam {
    private String name;
    private String email;
    private String rowPassword;

    public UserRegisterParam(String name,String email,String rowPassword){
        this.name = name;
        this.email = email;
        this.rowPassword = rowPassword;
    }
}
