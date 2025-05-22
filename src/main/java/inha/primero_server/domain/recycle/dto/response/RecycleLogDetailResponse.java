package inha.primero_server.domain.recycle.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RecycleLogDetailResponse {
    private Long recycleId;
    private String binLocation;
    private String recordImgPath;
    private LocalDateTime takenAt;
    private Boolean result;
    private Integer statusCode;
    private String message;
} 