package org.example.core.user;

import org.example.api.exception.AlreadyRegisterException;

public interface UserRepository {
    User save(User user);
    boolean existsByEmail(String email) throws AlreadyRegisterException;
    User findUserByEmail(String email);
}
