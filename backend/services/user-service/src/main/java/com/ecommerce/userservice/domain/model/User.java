package com.ecommerce.userservice.domain.model;

import com.ecommerce.userservice.domain.model.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdAt;

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    public boolean isUser() {
        return this.role == Role.USER;
    }

    public boolean isGuest() {
        return this.role == Role.GUEST;
    }
}
