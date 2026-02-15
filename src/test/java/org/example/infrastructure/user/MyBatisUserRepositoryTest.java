package org.example.infrastructure.user;

import org.example.core.user.User;
import org.example.core.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@MybatisTest
@Import(UserRepository.class)
public class MyBatisUserRepositoryTest {

    private UserRepository userRepository;

    @Test
    void save_user_success() {
        User user = new User("testName","test@example.com");
        userRepository.save(user);
        Optional<User> found = userRepository.findUserByEmail("test@exmaple.com");

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("testName",found.get().getName());
    }
}
