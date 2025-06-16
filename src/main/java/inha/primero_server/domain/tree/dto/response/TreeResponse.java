package inha.primero_server.domain.tree.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TreeResponse (
        @Schema(description = "tree id", example = "1")
        Long id,
        @Schema(description = "photo 저장 경로", example = "")
        String photoPath,
        String pinColor,
        double latitude,
        double longitude
) {}
