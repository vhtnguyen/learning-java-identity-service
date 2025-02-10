package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.RoleCreationRequest;
import com.devteria.identity_service.dto.response.RoleResponse;
import com.devteria.identity_service.entity.Permission;
import com.devteria.identity_service.entity.Role;
import com.devteria.identity_service.exception.AppRuntimeException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.mapper.RoleMapper;
import com.devteria.identity_service.repository.PermissionRepository;
import com.devteria.identity_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleCreationRequest request) {
        Role newRole = roleMapper.fromRoleCreationRequest(request);
        Set<Permission> permissions = new HashSet<Permission>();
                request.getPermissions()
                        .forEach(permissionName -> {
                            var permission =
                                    permissionRepository.findByName(permissionName).orElseThrow(() -> new AppRuntimeException(ErrorCode.PERMISSION_NOT_FOUND));
                            permissions.add(permission);
                        });
        newRole.setPermissions(permissions);
        roleRepository.save(newRole);
        return roleMapper.toRoleResponse(newRole);
    }
    public RoleResponse findByName(String name) {
        var role =
                roleRepository.findByName(name).orElseThrow(() -> new AppRuntimeException(ErrorCode.ROLE_NOT_FOUND));
        return roleMapper.toRoleResponse(role);
    }
    public List<RoleResponse> getAll(){
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }


}
