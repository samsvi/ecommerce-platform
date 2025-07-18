package com.ecommerce.userservice.application.mapper;

public interface BaseMapper<D, M> {
    D toDto(M domain);
    M toDomain(D dto);
}
