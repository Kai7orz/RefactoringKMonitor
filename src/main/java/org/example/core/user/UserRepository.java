package org.example.core.user;

import org.example.api.exception.AlreadyRegisterException;
import org.example.application.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);
    boolean existsByEmail(String email) throws AlreadyRegisterException;
    Optional<User> findUserByEmail(String email);
    Role findRoleById(Integer userId);
    void delete(Integer userId);
}
