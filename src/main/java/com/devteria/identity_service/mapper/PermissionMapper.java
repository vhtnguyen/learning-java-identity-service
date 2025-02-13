package com.devteria.identity_service.mapper;

import com.devteria.identity_service.dto.request.PermissionCreationRequest;
import com.devteria.identity_service.dto.response.PermissionResponse;
import com.devteria.identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission fromPermissionCreationRequest(PermissionCreationRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}