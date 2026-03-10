package org.example.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegisterParam {
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "不正な文字が含まれています")
    @NotBlank
    private final String name;
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    @Size(min=8,max=100)
    private final String passwordRow;

    public UserRegisterParam(String name,String email,String passwordRow){
        this.name = name;
        this.email = email;
        this.passwordRow = passwordRow;
    }
}
