package com.ecommerce.userservice.infrastructure.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    String path;
    String message;
    int statusCode;
    ZonedDateTime zonedDateTime;
    List<String> errors;
}
