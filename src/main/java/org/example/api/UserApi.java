package org.example.api;

import lombok.AllArgsConstructor;
import org.example.application.UserLoginParam;
import org.example.application.UserRegisterParam;
import org.example.application.UserService;

@AllArgsConstructor
public class UserApi{
  private UserService userService;
  private AuthService authService;

  public User registerResponse(UserRegisterParam userRegisterParam){
    // registerResponse 内で入力形式チェックして，不正なら FieldErrorResource を throw する
    // userRegisterParam から適宜パラメータを受信
    // AuthService の registerUser を呼び出す
      User user = authService.registerUser(userRegisterParam);
  }

  public UserLoginParam loginUer(){

  }
}