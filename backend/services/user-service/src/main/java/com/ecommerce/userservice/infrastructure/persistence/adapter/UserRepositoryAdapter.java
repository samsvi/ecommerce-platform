package com.ecommerce.userservice.infrastructure.persistence.adapter;

import com.ecommerce.userservice.application.mapper.UserMapper;
import com.ecommerce.userservice.domain.model.User;
import com.ecommerce.userservice.domain.port.outbound.UserRepository;
import com.ecommerce.userservice.infrastructure.mapper.UserEntityMapper;
import com.ecommerce.userservice.infrastructure.persistence.entity.UserEntity;
import com.ecommerce.userservice.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserEntityMapper userEntityMapper;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository, UserEntityMapper userEntityMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(userEntityMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        UserEntity entity = userEntityMapper.toEntity(user);
        UserEntity saved = jpaUserRepository.save(entity);
        return userEntityMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }
}
