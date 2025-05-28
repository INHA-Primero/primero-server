package inha.primero_server.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private Long userId;
    private String email;
    private String name;
    private int studentNumber;
    private String nickname;
    private String profileImgPath;
    private Integer totalPoint;
} 