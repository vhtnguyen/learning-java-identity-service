package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.AuthenticationRequest;
import com.devteria.identity_service.dto.request.IntrospectRequest;
import com.devteria.identity_service.dto.request.LogoutRequest;
import com.devteria.identity_service.dto.response.AuthenticationResponse;
import com.devteria.identity_service.dto.response.IntrospectResponse;
import com.devteria.identity_service.entity.InvalidToken;
import com.devteria.identity_service.exception.AppRuntimeException;
import com.devteria.identity_service.exception.ErrorCode;
import com.devteria.identity_service.repository.InvalidTokenRepository;
import com.devteria.identity_service.repository.UserRepository;
import com.devteria.identity_service.utils.TokenProvider;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    TokenProvider tokenProvider;
    PasswordEncoder passwordEncoder;
    InvalidTokenRepository invalidTokenRepository;



    public IntrospectResponse introspect(IntrospectRequest request){
        String token = request.getToken();
        boolean isValidToken=false;
        try {
            boolean isVerified = tokenProvider.verifyToken(token);
            SignedJWT signedToken = SignedJWT.parse(token);
            boolean isDateValid = signedToken.getJWTClaimsSet().getExpirationTime().after(new Date());
            boolean isNotBlackListed = false;
            if (isDateValid) {
                String jit = signedToken.getJWTClaimsSet().getJWTID();
                isNotBlackListed = invalidTokenRepository.findById(jit).isEmpty();
            }
            isValidToken = isVerified && isDateValid && isNotBlackListed;
        }
        catch (Exception exception){
            throw  new AppRuntimeException(ErrorCode.BAD_REQUEST);
        }
        return IntrospectResponse.builder()
                .valid(isValidToken)
                .build();
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
    public void logout(LogoutRequest request){
        String token = request.getToken();
        try {
            boolean verified = tokenProvider.verifyToken(token);
            if (!verified) {
                return;
            }
            SignedJWT signedToken = SignedJWT.parse(token);
            Date expiryTime = signedToken.getJWTClaimsSet().getExpirationTime();
            if (expiryTime.after(new Date())) {
                String jit = signedToken.getJWTClaimsSet().getJWTID();
                InvalidToken invalidToken =
                        InvalidToken.builder().id(jit).expireTime(expiryTime).build();
                invalidTokenRepository.save(invalidToken);
            }
        }
        catch (Exception exception){
            throw  new AppRuntimeException(ErrorCode.BAD_REQUEST);
        }

    }
}
