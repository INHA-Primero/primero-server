package inha.primero_server.domain.user.dto.response;

import inha.primero_server.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {
    private final String email;
    private final String name;
    private final String studentId;
    private final String nickname;
    private final boolean emailVerified;
    private final LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;
    private final String profileImageUrl;
    private final String barcode;
    private final int level;
    private final int expGauge;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .studentId(user.getStudentId())
                .nickname(user.getNickname())
                .emailVerified(user.isEmailVerified())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .profileImageUrl(user.getProfileImageUrl())
                .barcode(user.getBarcode())
                .level(user.getLevel())
                .expGauge(user.getExpGauge())
                .build();
    }
} 