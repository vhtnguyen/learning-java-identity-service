package com.devteria.identity_service.mapper;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.request.UserPatchRequest;
import com.devteria.identity_service.dto.request.UserPutRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromUserCreationRequest(UserCreationRequest request);

    void fromUserUpdateRequest(@MappingTarget User user, UserPutRequest request);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUserPatchRequest(@MappingTarget User user, UserPatchRequest request);
    UserResponse toUserResponse(User user);
}
