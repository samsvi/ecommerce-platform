package com.ecommerce.userservice.infrastructure.mapper;

import com.ecommerce.userservice.application.dto.request.RegisterRequest;
import com.ecommerce.userservice.application.mapper.BaseMapper;
import com.ecommerce.userservice.domain.model.User;
import com.ecommerce.userservice.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mapping(target = "role", expression = "java(com.ecommerce.userservice.domain.model.enums.Role.USER)")
    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);
}
