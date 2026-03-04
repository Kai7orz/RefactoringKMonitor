package org.example.infrastructure.repository;

import org.example.application.Role;
import org.example.infrastructure.mybatis.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository implements org.example.core.RoleRepository {
    private RoleMapper roleMapper;

    @Autowired
    public RoleRepository(RoleMapper roleMapper){
        this.roleMapper = roleMapper;
    }

    public void save(Role role){
        this.roleMapper.insert(role.getName());
    }
    public Role findRoleByName(String name) {
        return this.roleMapper.findRoleByName(name);
    }
}
