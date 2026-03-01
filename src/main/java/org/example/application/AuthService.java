package org.example.application;

import org.example.api.exception.AlreadyRegisterException;
import org.example.core.user.User;
import org.example.core.user.UserRepository;
import org.example.core.userCredential.UserCredential;
import org.example.core.userCredential.UserCredentialRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import org.example.api.exception.AuthenticationException;

@Service
public class AuthService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserCredentialRepository userCredentialRepository;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserCredentialRepository userCredentialRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userCredentialRepository = userCredentialRepository;
    }

    @Transactional
    public User registerUser(UserRegisterParam userRegisterParam) {
        // transaction で 一括で処理すること
        // 重複ユーザーへはエラーを返すこと
        // Email に Unique 制約を設けて, DB でエラーを返してもらう設計？
        if(this.userRepository.existsByEmail(userRegisterParam.getEmail())){
            throw new AlreadyRegisterException("このメールアドレスは既に登録されています");
        }

        User user = new User(userRegisterParam.getName(), userRegisterParam.getEmail());
        // アプリ側で重複ユーザーがいないか事前にチェックする
        // repository に user を登録
        User savedUser = this.userRepository.save(user);

        UserCredential userCredential = new UserCredential(savedUser.getId(), this.passwordEncoder.encode(userRegisterParam.getRowPassword()));
        this.userCredentialRepository.save(userCredential);

        return savedUser;
    }

    @Transactional
    public User loginUser(UserLoginParam userLoginParam){
        // email を基に DBからuser を取得して，その user に紐づいた Credential をとり，そのcredential と userLoginParam を Encode したものを比較する
        User registeredUser = this.userRepository.findUserByEmail(userLoginParam.getEmail()).orElseThrow(()-> new AuthenticationException("ユーザーが見つかりません"));
        UserCredential userCredential = this.userCredentialRepository.get(registeredUser.getId()).orElseThrow(()-> new AuthenticationException("認証情報が登録されていません"));
        if(this.passwordEncoder.matches(userLoginParam.getPasswordRow(),userCredential.getPasswordHash())){
            return registeredUser;
        } else{
            throw new AuthenticationException("認証エラー");
        }
    }
}
