package org.example.core.userCredential;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository {
    void save(UserCredential credential);
    Optional<UserCredential> get(Integer userId);
}
