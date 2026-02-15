package org.example.core.userCredential;

import lombok.Getter;

@Getter
public class UserCredential {
    private Integer userId;
    private String passwordHash;

    public UserCredential(Integer userId,String passwordHash){
        this.userId = userId;
        this.passwordHash = passwordHash;
    }
}
