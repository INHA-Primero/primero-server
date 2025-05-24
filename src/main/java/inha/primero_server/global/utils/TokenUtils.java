package inha.primero_server.global.utils;

import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import org.springframework.util.StringUtils;

public class TokenUtils {
    private TokenUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String extractToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new CustomException(ErrorCode.INVALID_INPUT, "유효하지 않은 토큰입니다.");
    }
} 