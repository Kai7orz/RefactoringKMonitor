package org.example.core.userCredential;

import lombok.Getter;

@Getter
public class UserCredential {
    private final Integer userId;
    private final String passwordHash;

    public UserCredential(Integer userId,String passwordHash){
        this.userId = userId;
        this.passwordHash = passwordHash;
    }
}
