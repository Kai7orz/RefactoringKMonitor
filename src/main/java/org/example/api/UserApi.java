package org.example.api;

import lombok.AllArgsConstructor;
import org.example.application.AuthService;
import org.example.application.UserRegisterParam;
import org.example.application.UserService;
import org.example.application.UserWithToken;
import org.example.core.JwtService;
import org.example.core.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserApi{
  private UserService userService;
  private AuthService authService;
  private JwtService jwtService;

  @PostMapping("/auth/register")
  public ResponseEntity<UserWithToken> registerResponse(@RequestBody UserRegisterParam userRegisterParam){
    // registerResponse 内で入力形式チェックして，不正なら FieldErrorResource を throw する
    // userRegisterParam から適宜パラメータを受信
    // AuthService の registerUser を呼び出す
    User user = this.authService.registerUser(userRegisterParam);
    // user  に紐づいた role の取得


    String newToken = this.jwtService.toToken(user,"test");
    // test 時にコンパイルエラー消しておくための仮置き
    UserWithToken testUserWithToken = new UserWithToken(user,"testToken");
    return ResponseEntity.status(201)
            .body(testUserWithToken);
  }

}
//  public UserLoginParam loginUer(){
//
//  }
//}