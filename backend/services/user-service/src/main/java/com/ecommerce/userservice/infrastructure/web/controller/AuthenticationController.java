package com.ecommerce.userservice.infrastructure.web.controller;

import com.ecommerce.userservice.application.dto.request.AuthenticationRequest;
import com.ecommerce.userservice.application.dto.request.RegisterRequest;
import com.ecommerce.userservice.application.dto.response.AuthenticationResponse;
import com.ecommerce.userservice.domain.model.User;
import com.ecommerce.userservice.domain.port.inbound.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {

        AuthenticationResponse response = authenticationService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {

        AuthenticationResponse response = authenticationService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }
}
