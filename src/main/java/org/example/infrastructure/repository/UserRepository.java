package org.example.infrastructure.repository;

import org.example.api.exception.AlreadyRegisterException;
import org.example.core.user.User;
import org.example.infrastructure.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements org.example.core.user.UserRepository
{
    private UserMapper userMapper;

    @Autowired
    public UserRepository(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public boolean existsByEmail(String email) {
        return this.userMapper.existsByEmail(email);
    }

    public User save(User user){
        if(this.existsByEmail(user.getEmail())) throw new AlreadyRegisterException("すでに登録されているユーザーです");
        this.userMapper.insert(user);
        return user;
    }

    public Optional<User> findUserByEmail(String email){
        return Optional.ofNullable(this.userMapper.findUserByEmail(email));
    }

    public void delete(Integer userId) {
        this.userMapper.delete(userId);
    }
}
