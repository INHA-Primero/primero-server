package inha.primero_server.domain.inquiry.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InquiryRequest (

    @NotBlank
    @Size(max = 100)
    @Schema(description = "문의 제목", example = "교환 환불 문의")
    String title,

    @NotBlank
    @Schema(description = "문의 내용", example = "제품에 이상이 있어 환불 부탁드립니다.")
    String content
) {}
