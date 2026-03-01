package org.example.application;

import lombok.Getter;

@Getter
public class DeleteParam {
    private Integer userId;
    private String currentPassword;

    public DeleteParam(Integer userId,String currentPassword) {
        this.userId = userId;
        this.currentPassword = currentPassword;
    }
}
