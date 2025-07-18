package com.ecommerce.userservice.domain.port.outbound;

import com.ecommerce.userservice.domain.model.User;
import io.jsonwebtoken.Claims;

import java.util.Optional;
import java.util.function.Function;

public interface JwtTokenProvider {

    String generateToken(User user);

    String extractUsername(String token);

    boolean isTokenValid(String token, User user);

    boolean validateToken(String token);

    Optional<User> getCurrentUser(String token);
}
