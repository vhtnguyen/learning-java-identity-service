package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.response.ApiResponse;
import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserPatchRequest;
import com.devteria.identity_service.dto.request.UserPutRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {

        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setResult(userService.getUsers());
        return response;
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String id) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.getUser(id));
        return response;
    }
    @GetMapping("/me")
    ApiResponse<UserResponse> getMyInfo() {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.getMyInfo());
        return response;
    }

    @PostMapping()
    ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreationRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.createUser(request));
        return response;
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> putUpdateUser(@PathVariable("userId") String id, @Valid @RequestBody UserPutRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.putUpdateUser(id, request));
        return response;
    }

    @PatchMapping("/{userId}")
    ApiResponse<UserResponse> patchUpdateUser(@PathVariable("userId") String id, @RequestBody UserPatchRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.pathUpdateUser(id, request));
        return response;

    }

    @DeleteMapping("/{userId}")
    ApiResponse<Object> deteleUser(@PathVariable("userId") String id) {
        ApiResponse<Object> response = new ApiResponse<>();
        userService.deleteUser(id);
        response.setMessage(String.format("User %s has been deleted", id));
        return response;
    }

}
