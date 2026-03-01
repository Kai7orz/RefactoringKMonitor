package org.example.core.userCredential;

import org.example.application.UpdatePasswordParam;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository {
    void save(UserCredential credential);
    Optional<UserCredential> get(Integer userId);
    void update(Integer userId,String newPasswordHash);
    void delete(Integer userId);
}
