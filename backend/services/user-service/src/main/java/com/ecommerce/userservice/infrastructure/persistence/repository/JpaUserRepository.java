package com.ecommerce.userservice.infrastructure.persistence.repository;

import com.ecommerce.userservice.domain.model.User;
import com.ecommerce.userservice.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(Long id);

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
