package inha.primero_server.domain.inquiry.controller;

import inha.primero_server.domain.inquiry.dto.request.AnswerRequest;
import inha.primero_server.domain.inquiry.dto.request.InquiryRequest;
import inha.primero_server.domain.inquiry.dto.response.AnswerResponse;
import inha.primero_server.domain.inquiry.dto.response.InquiryPagingResponse;
import inha.primero_server.domain.inquiry.dto.response.InquiryResponse;
import inha.primero_server.domain.inquiry.service.AnswerService;
import inha.primero_server.domain.inquiry.service.InquiryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * InquiryController : 문의 관련 엔드포인트를 처리.
 */
@Tag(name = "inquiry controller", description = "문의 API")
@RestController
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    private final AnswerService answerService;

    /**
     * 유저 문의글 작성 API
     *
     * @param inquiryRequest 문의글 작성 요청
     * @return 문의글 작성 결과를 포함하는 응답 ResponseEntity<InquiryRes>
     */
    @PostMapping()
    @Operation(summary = "문의 생성 API", description = "현재 로그인한 사용자가 문의를 생성합니다.")
    public ResponseEntity<InquiryResponse> createInquiry(@RequestHeader(name = "X-USER-ID") Long userId,
                                                         @Validated @RequestBody InquiryRequest inquiryRequest) {//@AuthenticationPrincipal UserPrincipal principal 추후 변경
        return ResponseEntity.ok(inquiryService.createInquiry(inquiryRequest, userId));
    }
    /*
    @PostMapping
    public ResponseEntity<InquiryRes> createInquiry(@RequestBody InquiryReq inquiryReq, User user) {//@AuthenticationPrincipal UserPrincipal principal 추후 변경
        return ResponseEntity.ok(inquiryService.createInquiry(inquiryReq, user));
    }
    */

    /**
     * 단일 문의글 조회 API
     *
     * @param inquiryId 문의글 식별자
     * @return 검색 문의 글을 포함하는 응답 ResponseEntity<InquiryRes>
     */
    @GetMapping("/{inquiryId}")
    @Operation(summary = "문의 조회 API", description = "단일 문의를 조회합니다.")
    public ResponseEntity<InquiryResponse> getOneInquiry(@PathVariable Integer inquiryId) {
        return ResponseEntity.ok(inquiryService.getInquiry(inquiryId));
    }

    /**
     * 페이징 및 필터링 결과 조회 API, 요청 예 /inquiry?page=0&size=10&sort=createdAt,desc
     * Default page : page = 0, size = 10
     *
     * @param pageable 페이징 조건
     * @param keyword 필터링 조건
     * @return 페이징 및 필터링 결과를 포함하는 응답 ResponseEntity<InquiryPagingRes>
     */
    @GetMapping
    @Operation(summary = "문의 조건 리스트 조회 API", description = "문의를 페이징 및 키워드 조건으로 리스트 조회합니다.")
    public ResponseEntity<InquiryPagingResponse> list(
            @PageableDefault(
                    sort = "createdAt", direction = Sort.Direction.DESC
            ) Pageable pageable,
            @RequestParam(required = false) String keyword) {
        InquiryPagingResponse pagingRes = inquiryService.list(pageable, keyword);

        return ResponseEntity.ok(pagingRes);
    }

    /**
     * 단일 문의글 수정 API
     * @param inquiryId 문의글 식별자
     * @param inquiryRequest 수정할 내역을 포함하는 요청
     * @return 수정된 문의글 식별자를 포함하는 응답 ResponseEntity<Integer>
     */
    @PutMapping("/{inquiryId}")
    @Operation(summary = "문의 수정 API", description = "단일 문의를 수정합니다.")
    public ResponseEntity<Integer> updateInquiry(@PathVariable Integer inquiryId,
                                                 @Validated @RequestBody InquiryRequest inquiryRequest) {
        inquiryService.updateInquiry(inquiryId, inquiryRequest);
        return ResponseEntity.ok(inquiryId);
    }

    /**
     * 단일 문의글 삭제 API
     *
     * @param inquiryId 문의글 식별자
     * @return 삭제된 문의글 식별자를 포함하는 응답 ResponseEntity<Integer>
     */
    @DeleteMapping("/{inquiryId}")
    @Operation(summary = "문의 삭제 API", description = "문의를 삭제합니다.")
    public ResponseEntity<Integer> deleteInquiry(@PathVariable Integer inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
        return ResponseEntity.ok(inquiryId);
    }

    /**
     * 사용자 문의 답변 API (ROLE : ADMIN)
     *
     * @param inquiryId 문의글 식별자
     * @param answerRequest 답변 요청
     * @return 답변 내역을 포함하는 ResponseEntity<AnswerRes>
     */
    @PostMapping("/{inquiryId}/answers")
    @Operation(summary = "답변 작성 API", description = "문의에 답변을 작성합니다.")
    public ResponseEntity<AnswerResponse> createAnswer(
            @PathVariable Integer inquiryId,
            @Validated @RequestBody AnswerRequest answerRequest
    ) {
        AnswerResponse answerResponse = answerService.createAnswer(inquiryId, answerRequest);
        return ResponseEntity.ok(answerResponse);
    }

    /**
     * 사용자 문의 답변 수정 API (ROLE : ADMIN)
     *
     * @param answerId 답변글 식별자
     * @param answerRequest 답변 요청
     * @return 답변글 수정 내역을 포함하는 ResponseEntity<AnswerRes>
     */
    @PutMapping("/{inquiryId}/answers/{answerId}")
    @Operation(summary = "답변 수정 API", description = "문의에 달린 답변을 수정합니다.")
    public ResponseEntity<AnswerResponse> updateAnswer(
            @PathVariable Integer answerId,
            @Validated @RequestBody AnswerRequest answerRequest
    ) {
        AnswerResponse answerResponse = answerService.updateAnswer(answerId, answerRequest);
        return ResponseEntity.ok(answerResponse);
    }

    /**
     * 사용자 문의 답변 삭제 API
     *
     * @param answerId 답변글 식별자
     * @param inquiryId 문의글 식별자
     * @return 답변글 삭제 내역을 포함하는 ResponseEntity<AnswerRes>
     */
    @DeleteMapping("/{inquiryId}/answers/{answerId}")
    @Operation(summary = "답변 삭제 API", description = "문의에 달린 답변을 삭제합니다.")
    public ResponseEntity<AnswerResponse> deleteAnswer(@PathVariable Integer answerId, @PathVariable Integer inquiryId) {
        AnswerResponse answerResponse = answerService.deleteAnswer(answerId, inquiryId);
        return ResponseEntity.ok((answerResponse));
    }
}
