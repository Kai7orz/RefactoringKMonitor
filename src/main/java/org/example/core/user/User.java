package org.example.core.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    private Integer id;
    private Integer roleId;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "不正な文字が含まれています")
    private String name;
    @NotBlank
    @Email
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public User(Integer roleId, String name, @Valid String email) {
        this.id = null;
        this.roleId = roleId;
        this.name = HtmlUtils.htmlEscape(name);
        this.email = HtmlUtils.htmlEscape(email);
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
