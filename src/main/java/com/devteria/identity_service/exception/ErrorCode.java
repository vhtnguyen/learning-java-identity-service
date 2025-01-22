package com.devteria.identity_service.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    USER_EXISTED(1002,"User existed"),
    USER_NOT_EXISTED(1003, "User not exited"),
    PASSWORD_TOO_SHORT(1004, "Password must be at least 6 characters"),
    FIELD_REQUIRED(1005, "Field is required"),
    INVALID_MESSAGE_KEY(999, "Invalid message key"),
    UNKNOWN_ERROR(666,"Uncategorized exception")
    ;
    final int code;
    final String message;
}
