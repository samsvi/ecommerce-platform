package com.ecommerce.userservice.infrastructure.security.adapter;

import com.ecommerce.userservice.application.dto.request.RegisterRequest;
import com.ecommerce.userservice.application.dto.response.AuthenticationResponse;
import com.ecommerce.userservice.domain.exception.UserAlreadyExistsException;
import com.ecommerce.userservice.domain.exception.UserNotFoundException;
import com.ecommerce.userservice.domain.model.User;
import com.ecommerce.userservice.domain.model.enums.Role;
import com.ecommerce.userservice.domain.port.inbound.AuthenticationService;
import com.ecommerce.userservice.domain.port.outbound.JwtTokenProvider;
import com.ecommerce.userservice.domain.port.outbound.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationServiceAdapter implements AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceAdapter(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse registerUser(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user);

        return null;
    }

    @Override
    public AuthenticationResponse login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        String token = jwtTokenProvider.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void logout(String token) {

    }
}
