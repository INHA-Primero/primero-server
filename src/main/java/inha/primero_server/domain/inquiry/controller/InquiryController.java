package inha.primero_server.domain.inquiry.controller;

import inha.primero_server.domain.inquiry.dto.request.AnswerReq;
import inha.primero_server.domain.inquiry.dto.request.InquiryReq;
import inha.primero_server.domain.inquiry.dto.response.AnswerRes;
import inha.primero_server.domain.inquiry.dto.response.InquiryPagingRes;
import inha.primero_server.domain.inquiry.dto.response.InquiryRes;
import inha.primero_server.domain.inquiry.service.AnswerService;
import inha.primero_server.domain.inquiry.service.InquiryService;
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
@RestController
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    private final AnswerService answerService;
    /**
     * 유저 문의글 작성 API
     *
     * @param inquiryReq 문의글 작성 요청
     * @return 문의글 작성 결과를 포함하는 응답 ResponseEntity<InquiryRes>
     */
    @PostMapping("/{userId}")
    public ResponseEntity<InquiryRes> createInquiry(@PathVariable Integer userId,
                                                    @Validated @RequestBody InquiryReq inquiryReq) {//@AuthenticationPrincipal UserPrincipal principal 추후 변경
        return ResponseEntity.ok(inquiryService.createInquiry(inquiryReq, userId));
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
    public ResponseEntity<InquiryRes> getOneInquiry(@PathVariable Integer inquiryId) {
        return ResponseEntity.ok(inquiryService.getInquiry(inquiryId));
    }

    /**
     * 단일 문의글 수정 API
     * @param inquiryId 문의글 식별자
     * @param inquiryReq 수정할 내역을 포함하는 요청
     * @return 수정된 문의글 식별자를 포함하는 응답 ResponseEntity<Integer>
     */
    @PutMapping("/{inquiryId}")
    public ResponseEntity<Integer> updateInquiry(@PathVariable Integer inquiryId,
                                                 @Validated @RequestBody InquiryReq inquiryReq) {
        inquiryService.updateInquiry(inquiryId, inquiryReq);
        return ResponseEntity.ok(inquiryId);
    }

    /**
     * 단일 문의글 삭제 API
     *
     * @param inquiryId 문의글 식별자
     * @return 삭제된 문의글 식별자를 포함하는 응답 ResponseEntity<Integer>
     */
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<Integer> deleteInquiry(@PathVariable Integer inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
        return ResponseEntity.ok(inquiryId);
    }

    /**
     * 페이징 및 필터링 결과 조회 API, 요청 예 /inquiry?page=0&size=10&sort=createdAt,desc
     * Default page : page = 0, size = 10
     *
     * @param pageable 페이징 조건
     * @param searchKeyword 필터링 조건
     * @return 페이징 및 필터링 결과를 포함하는 응답 ResponseEntity<InquiryPagingRes>
     */
    @GetMapping
    public ResponseEntity<InquiryPagingRes> list(
            @PageableDefault(
                    sort = "createdAt", direction = Sort.Direction.DESC
            ) Pageable pageable,
            @RequestParam(required = false) String searchKeyword) {
        InquiryPagingRes pagingRes = inquiryService.list(pageable, searchKeyword);

        return ResponseEntity.ok(pagingRes);
    }

    // 7) Add Answer to Inquiry (admin)
    @PostMapping("/{inquiryId}/answers")
    public ResponseEntity<AnswerRes> createAnswer(
            @PathVariable Integer inquiryId,
            @Validated @RequestBody AnswerReq answerReq
    ) {
        AnswerRes answerRes = answerService.create(inquiryId, answerReq);
        inquiryService.markAnswered(inquiryId);
        return ResponseEntity.ok(answerRes);
    }
}
