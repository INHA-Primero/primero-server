package inha.primero_server.domain.recycle.dto.response;

import inha.primero_server.domain.recycle.entity.RecycleHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RecycleHistoryResponse {
    private final Long id;
    private final String barcode;
    private final boolean success;
    private final int earnedExp;
    private final String failReason;
    private final LocalDateTime createdAt;

    public static RecycleHistoryResponse from(RecycleHistory history) {
        return RecycleHistoryResponse.builder()
                .id(history.getId())
                .barcode(history.getBarcode())
                .success(history.isSuccess())
                .earnedExp(history.getEarnedExp())
                .failReason(history.getFailReason())
                .createdAt(history.getCreatedAt())
                .build();
    }
} 