package inha.primero_server.domain.inquiry.controller;

import inha.primero_server.domain.inquiry.dto.request.InquiryReq;
import inha.primero_server.domain.inquiry.dto.response.InquiryPagingRes;
import inha.primero_server.domain.inquiry.dto.response.InquiryRes;
import inha.primero_server.domain.inquiry.entity.Inquiry;
import inha.primero_server.domain.inquiry.entity.User;
import inha.primero_server.domain.inquiry.repository.InquiryRepository;
import inha.primero_server.domain.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    // 문의 글 생성
    @PostMapping("/{username}")
    public ResponseEntity<InquiryRes> createInquiry(@RequestBody InquiryReq req, @PathVariable String username) {
        return ResponseEntity.ok(inquiryService.createInquiry(req, username));
    }

    //문의 글 한 개 조회
    @GetMapping("/{id}")
    public ResponseEntity<InquiryRes> getOneInquiry(@PathVariable Integer id) {
        return ResponseEntity.ok(inquiryService.findById(id));
    }

    //글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateInquiry(@PathVariable Integer id, @RequestBody InquiryReq req) {
        inquiryService.updateInquiry(id, req);
        return ResponseEntity.ok(id);
    }

    //글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteInquiry(@PathVariable Integer id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.ok(id);
    }

    /**
     * GET /inquiry
     * 요청 예) /inquiry?page=0&size=10&sort=createdAt,desc
     */
    @GetMapping
    public ResponseEntity<InquiryPagingRes> getList(
            @PageableDefault(
                    page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC
            ) Pageable pageable) {
        Page<Inquiry> page = inquiryService.index(pageable);
        InquiryPagingRes pagingRes = InquiryPagingRes.from(page);
        return ResponseEntity.ok(pagingRes);
    }
}
