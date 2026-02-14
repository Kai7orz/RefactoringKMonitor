package org.example.core;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    private Integer id;
    private Integer role_id;
    private String name;
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public User(String name, String email) {
        this.id = null;
        this.role_id = null;
        this.name = name;
        this.email = email;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    public void updateUserInfo(String name, String email, Integer role_id) {
        this.name = name;
        this.email = email;
        this.role_id = role_id;
    }

    public void changePassword(String passwordHash){

    }
}
