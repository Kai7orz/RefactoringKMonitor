package org.example.core.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    private Integer id;
    private Integer roleId;
    private String name;
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public User(Integer roleId,String name, String email) {
        this.id = null;
        this.roleId = roleId;
        this.name = name;
        this.email = email;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    public void updateUserInfo(String name, String email, Integer roleId) {
        this.name = name;
        this.email = email;
        this.roleId = roleId;
    }

    public String getRole(){
        return "test";
    }
}
