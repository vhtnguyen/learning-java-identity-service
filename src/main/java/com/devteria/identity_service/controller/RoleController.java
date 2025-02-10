package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.RoleCreationRequest;
import com.devteria.identity_service.dto.response.ApiResponse;
import com.devteria.identity_service.dto.response.RoleResponse;
import com.devteria.identity_service.service.RoleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Slf4j
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    ApiResponse<List<RoleResponse>> getPermissions() {

        ApiResponse<List<RoleResponse>> response = new ApiResponse<>();
        response.setResult(roleService.getAll());
        return response;
    }

    @GetMapping("/{permissionName}")
    ApiResponse<RoleResponse> getUser(@PathVariable("permissionName") String name) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();
        response.setResult(roleService.findByName(name));
        return response;
    }


    @PostMapping()
    ApiResponse<RoleResponse> createUser(@Valid @RequestBody RoleCreationRequest request) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();
        response.setResult(roleService.createRole(request));
        return response;
    }


}
