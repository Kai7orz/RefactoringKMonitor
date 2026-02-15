package org.example.infrastructure.repository;

import org.example.core.user.User;
import org.example.infrastructure.mybatis.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements org.example.core.user.UserRepository
{
    private UserMapper userMapper;

    public UserRepository(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public boolean existsByEmail(String email) {
        return this.userMapper.existsByEmail(email);
    }

    public User save(User user){
        this.userMapper.insert(user);
        return user;
    }

    public Optional<User> findUserByEmail(String email){
        return Optional.ofNullable(this.userMapper.findUserByEmail(email));
    }
}
