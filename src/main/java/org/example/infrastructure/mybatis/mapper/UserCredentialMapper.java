package org.example.infrastructure.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.core.userCredential.UserCredential;

@Mapper
public interface UserCredentialMapper {
    void insert(@Param("credential")UserCredential userCredential);
    UserCredential get(@Param("userId") Integer userId);
    void delete(@Param("userId") Integer userId);
}
