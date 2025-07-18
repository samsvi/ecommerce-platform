package com.ecommerce.userservice.domain.port.inbound;

import com.ecommerce.userservice.application.dto.request.RegisterRequest;
import com.ecommerce.userservice.application.dto.response.AuthenticationResponse;
import com.ecommerce.userservice.domain.model.User;

import java.util.Optional;

public interface AuthenticationService {

    AuthenticationResponse registerUser(RegisterRequest request);

    AuthenticationResponse login(String email, String password);

    void logout(String token);
}
