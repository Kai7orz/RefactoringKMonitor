package org.example.application;

import org.example.core.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public User registerUser(UserRegisterParam userRegisterParam){
        // レスポンスの仮置き
        User user = new User("testName","test@example.com");
        return user;
    }
}
