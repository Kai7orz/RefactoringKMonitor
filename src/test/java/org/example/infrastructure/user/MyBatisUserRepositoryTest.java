package org.example.infrastructure.user;

import org.example.api.exception.AlreadyRegisterException;
import org.example.core.user.User;
import org.example.core.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.lang.Nullable;

import java.util.NoSuchElementException;
import java.util.Optional;

@MybatisTest
@Import(org.example.infrastructure.repository.UserRepository.class)
public class MyBatisUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void return_True_when_email_exists() {
        User user = new User("testName","test@example.com");
        this.userRepository.save(user);
        Assertions.assertTrue(this.userRepository.existsByEmail(user.getEmail()));
    }

    @Test
    void return_False_when_email_not_exists() {
        User user = new User("testName","test@example.com");
        Assertions.assertFalse(this.userRepository.existsByEmail(user.getEmail()));
    }

    @Test
    void failed_to_get_not_registerd_user() {
        User user = new User("testName","test@example.com");
        Assertions.assertThrows(NoSuchElementException.class,()->{
            this.userRepository.findUserByEmail(user.getEmail()).get();
        });
    }

    @Test
    void save_user_generate_id() {
        User user = new User("testName","test@example.com");
        this.userRepository.save(user);
        User registerdUser = userRepository.findUserByEmail(user.getEmail()).orElseThrow();
        Assertions.assertEquals(1,registerdUser.getId());
    }

    @Test
    void save_user_success() {
        User user = new User("testName","test@example.com");
        this.userRepository.save(user);
        Optional<User> found = userRepository.findUserByEmail("test@example.com");

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("testName",found.get().getName());
    }

    @Test
    void delete_user_success() {
        User dummyUser = new User("testName","test@example.com");
        this.userRepository.save(dummyUser);
        User foundUser = this.userRepository.findUserByEmail(dummyUser.getEmail()).orElseThrow();
        Assertions.assertEquals(dummyUser.getName(),foundUser.getName());
        Assertions.assertEquals(dummyUser.getEmail(),foundUser.getEmail());
        Assertions.assertTrue(this.userRepository.existsByEmail(dummyUser.getEmail()));
        this.userRepository.delete(foundUser.getId());
        Assertions.assertFalse(this.userRepository.existsByEmail(dummyUser.getEmail()));
    }

    @Test
    void not_save_registered_user_fail() {
        User user = new User("testName","test@example.com");
        User registeredUser = new User("testName","test@example.com");
        this.userRepository.save(user);
        Assertions.assertThrows(AlreadyRegisterException.class,()->userRepository.save(registeredUser));
    }

}
