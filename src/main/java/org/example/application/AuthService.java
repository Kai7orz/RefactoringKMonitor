package org.example.application;

import org.example.core.user.User;
import org.example.core.user.UserRepository;
import org.example.core.userCredential.UserCredential;
import org.example.core.userCredential.UserCredentialRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public User registerUser(UserRegisterParam userRegisterParam){
        // transaction で 一括で処理すること
        User user = new User(userRegisterParam.getName(),userRegisterParam.getEmail());
        // repository に user を登録
        User savedUser = this.userRepository.save(user);

        UserCredential userCredential = new UserCredential(user.getId(),this.passwordEncoder.encode(userRegisterParam.getRowPassword()));
        UserCredential savedCredential = this.userCredentialRepository.save(userCredential);

        return user;
    }
}
