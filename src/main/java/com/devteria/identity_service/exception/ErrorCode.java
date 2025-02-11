package com.devteria.identity_service.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    INVALID_MESSAGE_KEY(999, "Invalid message key", HttpStatus.INTERNAL_SERVER_ERROR),
    UNCATEGORIZED_EXCEPTION(666, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(777, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(888, "Unauthorized", HttpStatus.FORBIDDEN),
    PERMISSION_NOT_FOUND(1001, "Permission not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1002, "Role not found", HttpStatus.NOT_FOUND),
    USER_EXISTED(1003, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1004, "User not existed", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1005, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_SHORT(1006, "Password must be at least 6 characters", HttpStatus.BAD_REQUEST),
    FIELD_REQUIRED(1007, "Field is required", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1008, "User must be at least {min} years old", HttpStatus.BAD_REQUEST);

    final int code;
    final String message;
    final HttpStatus statusCode;
}
