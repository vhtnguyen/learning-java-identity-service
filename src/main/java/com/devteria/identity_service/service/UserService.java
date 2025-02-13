package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserPatchRequest;
import com.devteria.identity_service.dto.request.UserPutRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.Role;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.exception.AppRuntimeException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.UserMapper;
import com.devteria.identity_service.repository.RoleRepository;
import com.devteria.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppRuntimeException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.fromUserCreationRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        var userRole=
                roleRepository.findById("USER").orElseThrow(()->new AppRuntimeException(ErrorCode.ROLE_NOT_FOUND));
        roles.add(userRole);
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));


    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getMyInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("username: {}", username);
        log.info("roles: {}", authentication.getAuthorities().toString());
        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppRuntimeException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
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

    @PreAuthorize("hasRole('ADMIN')")
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
