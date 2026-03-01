package org.example.infrastructure.repository;

import org.example.application.UpdatePasswordParam;
import org.example.core.userCredential.UserCredential;
import org.example.infrastructure.mybatis.mapper.UserCredentialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserCredentialRepository implements org.example.core.userCredential.UserCredentialRepository {

    // Mapper.xml 実装して，MapperInterface 定義して，その変数をここで定義（DI）
    private UserCredentialMapper userCredentialMapper;

    @Autowired
    public UserCredentialRepository(UserCredentialMapper userCredentialMapper){
        this.userCredentialMapper = userCredentialMapper;
    }

    public void save(UserCredential userCredential){
        this.userCredentialMapper.insert(userCredential);
    }

    public Optional<UserCredential> get(Integer userId){
        UserCredential userCredential = this.userCredentialMapper.get(userId);
        return Optional.ofNullable(userCredential);
    }

    public void update(UpdatePasswordParam updatePasswordParam){
        this.userCredentialMapper.update(updatePasswordParam);
    }
}
