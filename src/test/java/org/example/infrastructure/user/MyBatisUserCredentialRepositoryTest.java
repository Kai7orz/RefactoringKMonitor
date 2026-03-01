package org.example.infrastructure.user;


import org.example.api.exception.AlreadyRegisterException;
import org.example.application.UpdatePasswordParam;
import org.example.core.user.User;
import org.example.core.user.UserRepository;
import org.example.core.userCredential.UserCredential;
import org.example.core.userCredential.UserCredentialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.NoSuchElementException;

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

    @Test
    void update_password_success() {
        User dummyUser = new User("testName","test@example.com");
        this.userRepository.save(dummyUser);
        User registeredUser = this.userRepository.findUserByEmail(dummyUser.getEmail()).orElseThrow();
        UserCredential dummyCredential = new UserCredential(registeredUser.getId(),"passwordHash");
        this.userCredentialRepository.save(dummyCredential);
        UserCredential registeredCredential = this.userCredentialRepository.get(registeredUser.getId()).orElseThrow();
        Assertions.assertEquals("passwordHash",registeredCredential.getPasswordHash());

        this.userCredentialRepository.update(registeredUser.getId(),"newPasswordHash");
        UserCredential newCredential = this.userCredentialRepository.get(registeredUser.getId()).orElseThrow();
        Assertions.assertEquals("newPasswordHash",newCredential.getPasswordHash());
    }

    @Test
    void delete_credential_success() {
        User dummyUser = new User("testName","test@example.com");
        this.userRepository.save(dummyUser);
        User foundUser = this.userRepository.findUserByEmail(dummyUser.getEmail()).orElseThrow();

        UserCredential dummyCredential = new UserCredential(foundUser.getId(),"passwordHash");
        this.userCredentialRepository.save(dummyCredential);


        UserCredential foundCredential = this.userCredentialRepository.get(foundUser.getId()).orElseThrow();
        Assertions.assertEquals(dummyCredential.getPasswordHash(),foundCredential.getPasswordHash());
        this.userCredentialRepository.delete(foundUser.getId());
        Assertions.assertThrows(NoSuchElementException.class,()->{
            this.userCredentialRepository.get(foundUser.getId()).orElseThrow();
        });
    }

    @Test
    void get_not_registered_credential_fail() {
        User user = new User("testName","test@example.com");
        Assertions.assertThrows(NoSuchElementException.class,()->{
           this.userCredentialRepository.get(user.getId()).get();
        });
    }
}
