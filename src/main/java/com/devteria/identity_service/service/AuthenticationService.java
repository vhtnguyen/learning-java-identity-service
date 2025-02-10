package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.AuthenticationRequest;
import com.devteria.identity_service.dto.request.IntrospectRequest;
import com.devteria.identity_service.dto.response.AuthenticationResponse;
import com.devteria.identity_service.dto.response.IntrospectResponse;
import com.devteria.identity_service.exception.AppRuntimeException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.repository.UserRepository;
import com.devteria.identity_service.utils.TokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    TokenProvider tokenProvider;
    PasswordEncoder passwordEncoder;



    public IntrospectResponse introspect(IntrospectRequest request)
    {
        String token = request.getToken();
        try {
            boolean IsValid = tokenProvider.verifyToken(token);

            return IntrospectResponse.builder()
                    .valid(IsValid)
                    .build();
        }
        catch (Exception e){
            return IntrospectResponse.builder()
                    .valid(false)
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppRuntimeException(ErrorCode.USER_NOT_EXISTED));

        boolean isAuthenticated= passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isAuthenticated)
            throw new AppRuntimeException(ErrorCode.UNAUTHENTICATED);
        var token =  tokenProvider.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
}
