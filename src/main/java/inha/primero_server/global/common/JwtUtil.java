package inha.primero_server.global.common;

import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKeyString;

    private Key secretKey;

    private static final long EXPIRATION_HOURS = 2;

    @PostConstruct
    public void init() {
        byte[] decodedKey = java.util.Base64.getDecoder().decode(secretKeyString);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
        System.out.println("secret: " + secretKeyString);
    }

    /**
     * 토큰 생성
     */
    public String createAccessToken(Long userId, String email, String role) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusHours(EXPIRATION_HOURS).toInstant()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰에서 userId 추출
     */
    public Long getUserId(String token) {
        if (token == null || token.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_JWT, "토큰이 비어있습니다.");
        }
        Claims claims = parseToken(token);
        return claims.get("userId", Long.class);
    }


    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (CustomException e) {
            return false;
        }
    }

    /**
     * 내부: 토큰 파싱 & 검증
     */
    private Claims parseToken(String token) {
        if (token == null || token.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_JWT, "토큰이 전달되지 않았습니다.");
        }

        if (!token.startsWith("Bearer ")) {
            throw new CustomException(ErrorCode.INVALID_JWT, "Authorization 헤더가 Bearer 형식이 아닙니다.");
        }

        token = token.substring(7);

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_JWT, "JWT가 만료되었습니다.");
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_JWT, "유효하지 않은 JWT입니다.");
        }
    }




}
