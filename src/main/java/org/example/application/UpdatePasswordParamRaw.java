package org.example.application;

import lombok.Getter;

@Getter
public class UpdatePasswordParamRaw {
    private Integer userId;
    private String currentPassword;
    private String newPassword;

    public UpdatePasswordParamRaw(Integer userId, String currentPassword, String newPassword) {
        this.userId = userId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
