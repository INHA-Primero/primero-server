package inha.primero_server.domain.recycle.dto;

import inha.primero_server.domain.recycle.entity.Recycle;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecycleListResponseDto {
    private final Long id;
    private final Boolean result;
    private final LocalDateTime takenAt;
    private final String binLocation;

    public RecycleListResponseDto(Recycle recycle) {
        this.id = recycle.getId();
        this.result = recycle.getResult();
        this.takenAt = recycle.getTakenAt();
        this.binLocation = recycle.getBin().getLocation();
    }
} 