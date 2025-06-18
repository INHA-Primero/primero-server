package inha.primero_server.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRankingResponse {
    private String nickname;
    private Long ranking;
    private Integer totalPoint;
} 