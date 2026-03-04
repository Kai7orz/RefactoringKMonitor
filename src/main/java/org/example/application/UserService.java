package org.example.application;

import org.example.core.user.User;
import org.example.core.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String getRole(User user) {
        Role receivedRole = this.userRepository.findRoleById(user.getId());
        return receivedRole.getName();
    }
}
