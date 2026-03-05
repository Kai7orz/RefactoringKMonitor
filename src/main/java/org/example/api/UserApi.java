package org.example.api;

import lombok.AllArgsConstructor;
<<<<<<< Updated upstream
import org.example.application.AuthService;
import org.example.application.UserRegisterParam;
import org.example.application.UserService;
import org.example.application.UserWithToken;
=======
import org.example.application.*;
import org.example.core.JwtService;
>>>>>>> Stashed changes
import org.example.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi{
  private AuthService authService;

<<<<<<< Updated upstream
  @PostMapping("/auth/register")
  public ResponseEntity<UserWithToken> registerResponse(@RequestBody UserRegisterParam userRegisterParam){
    // registerResponse 内で入力形式チェックして，不正なら FieldErrorResource を throw する
    // userRegisterParam から適宜パラメータを受信
    // AuthService の registerUser を呼び出す
    User user = authService.registerUser(userRegisterParam);

    // test 時にコンパイルエラー消しておくための仮置き
    UserWithToken testUserWithToken = new UserWithToken(user,"testToken");
    return ResponseEntity.status(201)
            .body(testUserWithToken);
=======
  @Autowired
  public UserApi(UserService userService, AuthService authService, JwtService jwtService) {
    this.authService = authService;
    this.jwtService = jwtService;
>>>>>>> Stashed changes
  }

  @PostMapping("/auth/register")
  public ResponseEntity<UserWithToken> registerResponse(@RequestBody UserRegisterParam userRegisterParam){
    User user = this.authService.registerUser(userRegisterParam);
    String token = this.jwtService.toToken(user);
    UserWithToken userWithToken = new UserWithToken(user,token);

    return ResponseEntity.status(201)
            .body(userWithToken);
  }

  @PostMapping("/auth/login")
  public ResponseEntity<UserWithToken> loginResponse(@RequestBody UserLoginParam userLoginParam){
    User user = this.authService.loginUser(userLoginParam);
    String token = this.jwtService.toToken(user);
    UserWithToken userWithToken = new UserWithToken(user,token);

    return ResponseEntity.status(200)
            .body(userWithToken);
  }
}
