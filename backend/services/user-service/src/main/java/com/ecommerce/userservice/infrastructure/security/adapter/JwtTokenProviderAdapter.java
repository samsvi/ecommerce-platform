package com.ecommerce.userservice.infrastructure.security.adapter;

import com.ecommerce.userservice.domain.model.User;
import com.ecommerce.userservice.domain.port.outbound.JwtTokenProvider;
import com.ecommerce.userservice.domain.port.outbound.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtTokenProviderAdapter implements JwtTokenProvider {

    private static final String SECRET_KEY = "77217A25432A462D4A614E645266556A586E3272357538782F413F4428472B4B";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    private final UserRepository userRepository;

    public JwtTokenProviderAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getEmail());
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private String createToken(Map<String, Object> extraClaims, String subject) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 10 hours expiration
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, User user) {
        final String email = extractUsername(token);
        return (email.equals(user.getEmail()) && !isTokenExpired(token));
    }

    @Override
    public boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        String email = extractUsername(token);
        if (email == null || email.isEmpty()) {
            return false;
        }

        return userRepository.findByEmail(email)
                .map(user -> isTokenValid(token, user))
                .orElse(false);
    }

    @Override
    public Optional<User> getCurrentUser(String token) {
        String email = extractUsername(token);

        if (email == null || email.isEmpty()) {
            return Optional.empty();
        }

        return userRepository.findByEmail(email);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration (String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
