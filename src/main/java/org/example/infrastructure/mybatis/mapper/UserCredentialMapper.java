package org.example.infrastructure.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.application.UpdatePasswordParam;
import org.example.application.UpdatePasswordParamRaw;
import org.example.core.userCredential.UserCredential;

@Mapper
public interface UserCredentialMapper {
    void insert(@Param("credential") UserCredential userCredential);
    void update(@Param("userId") Integer userId,@Param("newPasswordHash") String newPasswordHash);
    UserCredential get(@Param("userId") Integer userId);
    void delete(@Param("userId") Integer userId);
}
