package org.example.infrastructure.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.core.user.User;

@Mapper
public interface UserMapper {
    void insert(@Param("user") User user);
    boolean existsByEmail(@Param("email") String email);
    User findUserByEmail(@Param("email") String email);
}
