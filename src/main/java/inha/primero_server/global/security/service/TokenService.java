package inha.primero_server.global.security.service;

import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import inha.primero_server.global.security.jwt.JwtTokenProvider;
import inha.primero_server.global.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtTokenProvider tokenProvider;

    public String createToken(String userUuid, String deviceUuid) {
        return tokenProvider.createToken(userUuid, deviceUuid);
    }

    public void invalidateToken(String token) {
        tokenProvider.invalidateToken(token);
    }

    public String validateAndGetUserUuid(String bearerToken) {
        String token = TokenUtils.extractToken(bearerToken);
        if (!tokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "유효하지 않은 토큰입니다.");
        }
        return tokenProvider.getUserUuid(token);
    }
} 