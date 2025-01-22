package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserPatchRequest;
import com.devteria.identity_service.dto.request.UserPutRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.exception.AppRuntimeException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.UserMapper;
import com.devteria.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppRuntimeException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.fromUserCreationRequest(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));


    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(user -> userMapper.toUserResponse(user)).collect(Collectors.toList());
    }

    public UserResponse getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppRuntimeException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse putUpdateUser(String id, UserPutRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppRuntimeException(ErrorCode.USER_NOT_EXISTED));
        userMapper.fromUserUpdateRequest(user, request);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public UserResponse pathUpdateUser(String id, UserPatchRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppRuntimeException(ErrorCode.USER_NOT_EXISTED));
        userMapper.fromUserPatchRequest(user, request);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
