package com.ecommerce.userservice.application.mapper;

import com.ecommerce.userservice.application.dto.request.RegisterRequest;
import com.ecommerce.userservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<RegisterRequest, User> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "role", expression = "java(com.ecommerce.userservice.domain.model.enums.Role.USER)")
    @Override
    User toDomain(RegisterRequest request);

    @Override
    RegisterRequest toDto(User entity);
}
