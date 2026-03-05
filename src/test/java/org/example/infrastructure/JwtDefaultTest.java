package org.example.infrastructure;

import org.example.api.exception.AuthenticationException;
import org.example.core.JwtService;
import org.example.core.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

public class JwtDefaultTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp(){
        this.jwtService  = new JwtDefault("123123123123123123123123123123123123123123123123123123123123",3600);
    }

    @Test
    void get_jwt_success() {
<<<<<<< Updated upstream
        User user = new User("testName","test@example.com");
        String testRole = "testRole";
=======
        Integer ROLE_ID = 2;
        User user = new User(ROLE_ID,"testName","test@example.com");
>>>>>>> Stashed changes
        // defaultJWT のservice を用いて, user,roleName を引数に token 返してもらう
        String receivedToken = this.jwtService.toToken(user);
        Assertions.assertNotNull(receivedToken);
        Assertions.assertEquals(ROLE_ID,this.jwtService.extractUserRoleId(receivedToken));
    }

    @Test
    void validate_token_success() {
<<<<<<< Updated upstream
        User user = new User("testName","test@example.com");
        String testRole = "testRole";
        String receivedToken = this.jwtService.toToken(user,testRole);
=======
        User user = new User(1,"testName","test@example.com");
        String receivedToken = this.jwtService.toToken(user);
>>>>>>> Stashed changes
        Assertions.assertTrue(this.jwtService.validateToken(receivedToken));
    }

    @Test
    void validate_wrong_token_fail() {
<<<<<<< Updated upstream
        User user = new User("testName","test@example.com");
        String testRole = "testRole";
        String receivedToken = this.jwtService.toToken(user,testRole);
=======
        User user = new User(1,"testName","test@example.com");
        String receivedToken = this.jwtService.toToken(user);
>>>>>>> Stashed changes
        Assertions.assertTrue(this.jwtService.validateToken(receivedToken));
        String invalidToken = "invalid_token";
        Exception e = Assertions.assertThrows(AuthenticationException.class,()->{
            this.jwtService.validateToken(invalidToken);
        });
        Assertions.assertEquals("無効なトークン",e.getMessage());
    }
}
