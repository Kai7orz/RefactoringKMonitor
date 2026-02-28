package org.example.infrastructure.user;


import org.example.core.user.User;
import org.example.core.user.UserRepository;
import org.example.core.userCredential.UserCredential;
import org.example.core.userCredential.UserCredentialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@MybatisTest
@Import({org.example.infrastructure.repository.UserCredentialRepository.class,
         org.example.infrastructure.repository.UserRepository.class})
public class MyBatisUserCredentialRepositoryTest {
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void save_user_credential_success() {
        User user = new User("testUser","test@example.com");
        this.userRepository.save(user);
        User registeredUser = this.userRepository.findUserByEmail("test@example.com").orElseThrow();
        Assertions.assertEquals("testUser",registeredUser.getName());
        UserCredential userCredential = new UserCredential(registeredUser.getId(),"hashedPassword");
        this.userCredentialRepository.save(userCredential);
        UserCredential registeredCredential = this.userCredentialRepository.get(registeredUser.getId()).get();
        Assertions.assertEquals(registeredCredential.getPasswordHash(),userCredential.getPasswordHash());
    }
}
