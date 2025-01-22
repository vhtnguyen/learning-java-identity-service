package com.devteria.identity_service.exception;

import com.devteria.identity_service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(value = Exception.class)
//    ResponseEntity<ApiResponse<Object>> handlingRuntimeException(Exception exception){
//        ApiResponse<Object> response = new ApiResponse<>();
//        response.setCode(ErrorCode.UNKNOWN_ERROR.getCode());
//        response.setMessage(ErrorCode.UNKNOWN_ERROR.getMessage());
//        return ResponseEntity.badRequest().body(response);
//    }
    @ExceptionHandler(value = AppRuntimeException.class)
    ResponseEntity<ApiResponse<Object>> handlingAppRuntimeException(AppRuntimeException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<Object> response = new ApiResponse<>();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError() != null ? exception.getFieldError().getDefaultMessage() : "UNKNOWN_ERROR";
        ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            errorCode = ErrorCode.INVALID_MESSAGE_KEY;

        }

        ApiResponse<Object> response = new ApiResponse<>();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}
