package inha.primero_server.domain.inquiry.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AnswerRequest (

    @NotBlank
    @Schema(description = "답변 내용", example = "확인했습니다. 3~5일 이내 환불될 예정입니다.")
    String content
) {}
