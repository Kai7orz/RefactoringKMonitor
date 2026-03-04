package org.example.core;

import org.example.application.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository
{
    void save(Role role);
    Role findRoleByName(String name);
}
