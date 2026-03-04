package org.example.core;

import org.example.core.user.User;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String toToken(User user, String roleName);
    boolean validateToken(String token);
    String extractUserRole(String token);
}
