package org.example.application;

import org.example.core.user.User;
import org.example.core.user.UserRepository;
import org.example.core.userCredential.UserCredentialRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserCredentialRepository userCredentialRepository;

    public AuthService(PasswordEncoder passwordEncoder,UserRepository userRepository,UserCredentialRepository userCredentialRepository){
            this.passwordEncoder = passwordEncoder;
            this.userRepository = userRepository;
            this.userCredentialRepository = userCredentialRepository;
    }

    public User registerUser(UserRegisterParam userRegisterParam){
        // レスポンスの仮置き
        // passwordEncoder 利用して userRegisterParam のパスワードハッシュ化する

        User user = new User(userRegisterParam.getName(),userRegisterParam.getEmail(),userRegisterParam.getR);
        // repository 呼んで User を登録する
        // repository の例外処理をどうするか考えておく
        return user;
    }
}
