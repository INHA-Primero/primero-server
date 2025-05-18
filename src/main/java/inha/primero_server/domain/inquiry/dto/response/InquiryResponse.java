package inha.primero_server.domain.inquiry.dto.response;

import inha.primero_server.domain.inquiry.entity.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record InquiryResponse (

    @Schema(description = "문의 ID", example = "1")
    Integer id,

    @Schema(description = "문의 제목", example = "교환 환불 문의")
    String title,

    @Schema(description = "신청 시간", example = "제품에 이상이 있어 환불 부탁드립니다.")
    String content,

    @Schema(description = "신청 시간", example = "OPEN")
    Status status,

    @Schema(description = "작성자", example = "Oh")
    String writer,

    @Schema(description = "답변 내용 리스트", example = "2024-08-01T00:00:00")
    List<AnswerResponse> answers,

    @Schema(description = "문의 등록 시간", example = "2024-08-01T00:00:00")
    LocalDateTime createdAt,

    @Schema(description = "문의 수정 시간", example = "2024-08-03T00:00:00")
    LocalDateTime lastModifiedAt
) {}
