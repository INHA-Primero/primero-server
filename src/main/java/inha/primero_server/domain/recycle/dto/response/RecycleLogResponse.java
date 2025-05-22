package inha.primero_server.domain.recycle.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecycleLogResponse {
    private Boolean success;
    private Integer point;
    private Integer statusCode;
    private String message;
} 