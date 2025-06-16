package inha.primero_server.domain.tree.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record TreeCreateRequest (
    @Schema(description = "위도", example = "42.195")
    double latitude,
    @Schema(description = "경도", example = "127.52")
    double longitude
){}