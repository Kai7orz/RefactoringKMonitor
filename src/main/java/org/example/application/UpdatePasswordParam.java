package org.example.application;

import lombok.Getter;

@Getter
public class UpdatePasswordParam {
        private Integer userId;
        private String currentPassword;
        private String newPasswordHash;

        public UpdatePasswordParam(Integer userId, String currentPassword, String newPasswordHash) {
            this.userId = userId;
            this.currentPassword = currentPassword;
            this.newPasswordHash = newPasswordHash;
        }
}
