package inha.primero_server.domain.login.dto.response;

import inha.primero_server.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private final String accessToken;
    private final Long userId;
    private final String email;
    private final String nickname;
    private final String role;
    private final int totalPoint;
    private final String barcodeUrl;

    public static LoginResponse of(String accessToken, User user) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role("USER")
                .totalPoint(user.getLevel() * 100 + user.getExpGauge())
                .barcodeUrl(user.getBarcode())
                .build();
    }
}