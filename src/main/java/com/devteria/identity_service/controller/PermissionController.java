package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.PermissionCreationRequest;
import com.devteria.identity_service.dto.response.ApiResponse;
import com.devteria.identity_service.dto.response.PermissionResponse;
import com.devteria.identity_service.service.PermissionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@Slf4j
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping
    ApiResponse<List<PermissionResponse>> getPermissions() {

        ApiResponse<List<PermissionResponse>> response = new ApiResponse<>();
        response.setResult(permissionService.getAll());
        return response;
    }

    @GetMapping("/{permissionName}")
    ApiResponse<PermissionResponse> getUser(@PathVariable("permissionName") String name) {
        ApiResponse<PermissionResponse> response = new ApiResponse<>();
        response.setResult(permissionService.findByName(name));
        return response;
    }

    @PostMapping()
    ApiResponse<PermissionResponse> createUser(@Valid @RequestBody PermissionCreationRequest request) {
        ApiResponse<PermissionResponse> response = new ApiResponse<>();
        response.setResult(permissionService.createPermission(request));
        return response;
    }


}
