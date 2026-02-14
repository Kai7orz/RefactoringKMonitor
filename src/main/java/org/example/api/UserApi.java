package org.example.api;

import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.example.application.AuthService;
import org.example.application.UserLoginParam;
import org.example.application.UserRegisterParam;
import org.example.application.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserApi{
  private UserService userService;
  private AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<User> registerResponse(UserRegisterParam userRegisterParam){
    // registerResponse 内で入力形式チェックして，不正なら FieldErrorResource を throw する
    // userRegisterParam から適宜パラメータを受信
    // AuthService の registerUser を呼び出す
    User user = authService.registerUser(userRegisterParam);
    return ResponseEntity.ok()
            .body(user);
  }

  public UserLoginParam loginUer(){

  }
}