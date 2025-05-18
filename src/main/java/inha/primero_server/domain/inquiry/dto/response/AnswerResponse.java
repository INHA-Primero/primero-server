package inha.primero_server.domain.inquiry.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AnswerResponse (

    @Schema(description = "답변 ID", example = "1")
    Integer id,

    @Schema(description = "답변 내용", example = "제품에 이상이 있어 환불 부탁드립니다.")
    String content,

    @Schema(description = "답변 시간", example = "2024-08-01T00:00:00")
    LocalDateTime createdAt,

    @Schema(description = "답변 수정 시간", example = "2024-08-03T00:00:00")
    LocalDateTime lastModifiedAt
) {}
