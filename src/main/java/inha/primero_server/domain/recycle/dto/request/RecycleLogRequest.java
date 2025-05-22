package inha.primero_server.domain.recycle.dto.request;

import lombok.Getter;

@Getter
public class RecycleLogRequest {
    private Long binId;
    private String imgUrl;
    private Long userId;
} 