package org.example.infrastructure.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.application.Role;

@Mapper
public interface RoleMapper {
    void insert(@Param("roleName") String roleName);
    Role findRoleByName(@Param("roleName") String roleName);
}
