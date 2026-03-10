package org.example.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.application.AuthService;
import org.example.application.UserRegisterParam;
import org.example.application.UserService;
import org.example.application.UserWithToken;
import org.example.application.*;
import org.example.core.JwtService;
import org.example.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {
    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public UserApi(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/auth/register", produces = "application/json")
    public ResponseEntity<UserWithToken> registerResponse(@Valid @RequestBody UserRegisterParam userRegisterParam) {
        User user = this.authService.registerUser(userRegisterParam);
        String token = this.jwtService.toToken(user);
        UserWithToken userWithToken = new UserWithToken(user, token);
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(userWithToken);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserWithToken> loginResponse(@RequestBody UserLoginParam userLoginParam) {
        User user = this.authService.loginUser(userLoginParam);
        String token = this.jwtService.toToken(user);
        UserWithToken userWithToken = new UserWithToken(user, token);

        return ResponseEntity.status(200)
                .body(userWithToken);
    }
}
