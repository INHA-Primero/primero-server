package inha.primero_server.domain.inquiry.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record InquiryPagingResponse (

    @Schema(description = "문의 내역 리스트", example = """
            [
                {
                  "title": "교환 환불 문의",
                  "content": "제품에 이상이 있어 환불 부탁드립니다.",
                  "status": "ANSWERED",
                  "writer": "string",
                  "answers": [
                                 {
                                   "content": "확인했습니다. 3~5일 이내 환불될 예정입니다.",
                                   "createdAt": "2025-05-18T00:45:59.318964",
                                   "lastModifiedAt": "2025-05-18T00:45:59.318964"
                                 },
                                 {
                                   "content": "확인했습니다.",
                                   "createdAt": "2025-05-18T00:46:00.386677",
                                   "lastModifiedAt": "2025-05-18T00:46:00.386677"
                                 },
                               ],
                  "createdAt": "2025-05-17T17:21:54.596027",
                  "lastModifiedAt": "2025-05-17T17:21:54.596027"
                },
                {
                  "title": "배달 기간 문의",
                  "content": "배달이 아직 도착하지 않아 질문드립니다.",
                  "status": "OPEN",
                  "writer": "string",
                  "answers": [],
                  "createdAt": "2025-05-17T17:21:53.498008",
                  "lastModifiedAt": "2025-05-17T17:21:53.498008"
                }
            ]
            """)
    List<InquiryResponse> inquiryRes,

    @Schema(description = "이전 페이지", example = "1")
    int previous,

    @Schema(description = "다음 페이지", example = "3")
    int next,

    @Schema(description = "현재 페이지", example = "2")
    int currentPage,

    @Schema(description = "전체 페이지", example = "5")
    int totalPages
) {}
