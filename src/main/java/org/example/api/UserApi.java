package org.example.api;

import lombok.AllArgsConstructor;
import org.example.application.UserLoginParam;
import org.example.application.UserRegisterParam;

@AllArgsConstructor
public class UserApi{
  private UserService userService;
  private AuthService authService;

  public RegisterResponse registerResponse(UserRegisterParam userRegisterParam){

  }

  public UserLoginParam loginUer(){

  }
}