package inha.primero_server.domain.inquiry.service;

import inha.primero_server.domain.inquiry.dto.request.InquiryReq;
import inha.primero_server.domain.inquiry.dto.response.InquiryPagingRes;
import inha.primero_server.domain.inquiry.dto.response.InquiryRes;
import inha.primero_server.domain.inquiry.entity.Inquiry;
import inha.primero_server.domain.inquiry.entity.User;
import inha.primero_server.domain.inquiry.entity.enums.Status;
import inha.primero_server.domain.inquiry.repository.InquiryRepository;
import inha.primero_server.domain.inquiry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.NoSuchElementException;

/**
 * InquiryServiceImpl : 문의 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class InquiryServiceImpl implements InquiryService{

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    /**
     * 문의글 작성
     *
     * @param inquiryReq 사용자 문의글 작성 요청
     * @return 문의글 작성 응답
     */
    @Override
    public InquiryRes createInquiry(InquiryReq inquiryReq, Integer userId) { //추후 AuthenticationPrincipal 추가
        // 1. 사용자 검증
        User user = validateUser(userId);

        // 2. Inquiry 생성 및 저장
        Inquiry inquiry = inquiryReq.toEntity();
        inquiry.setUser(user);
        inquiryRepository.save(inquiry);
        return new InquiryRes(inquiry);
    }

    private User validateUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User Not Found : " + userId));
    }

    /*@Override
    public InquiryRes createInquiry(InquiryReq inquiryReq, User user) { //추후 AuthenticationPrincipal 추가
        // 1. 사용자 검증
        validateUser(user);

        // 2. Inquiry 생성 및 저장
        Inquiry inquiry = inquiryReq.toEntity();
        inquiry.setUser(user);
        inquiryRepository.save(inquiry);
        return new InquiryRes(inquiry);
    }

    private void validateUser(User user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User Not Found : " + user));
    }
    */


    /**
     * 단일 문의글 조회
     *
     * @param inquiryId 문의글 식별자
     * @return 문의글 조회 응답
     */
    @Override
    public InquiryRes getInquiry(Integer inquiryId) {
        Inquiry inquiry = validateInquiry(inquiryId);
        return new InquiryRes(inquiry);
    }

    /**
     * 단일 문의글 수정
     *
     * @param inquiryId 문의글 식별자
     * @param inquiryReq 문의글 수정 요청
     */
    @Override
    public void updateInquiry(Integer inquiryId, InquiryReq inquiryReq) {
        Inquiry inquiry = validateInquiry(inquiryId);
        inquiry.update(inquiryReq);
    }

    /**
     * 단일 문의글 삭제
     * @param inquiryId 문의글 식별자
     */
    @Override
    public void deleteInquiry(Integer inquiryId) {
        Inquiry inquiry = validateInquiry(inquiryId);
        inquiryRepository.deleteById(inquiry.getId());
    }

    @Override
    public void markAnswered(Integer inquiryId) {
        Inquiry inquiry = validateInquiry(inquiryId);
        inquiry.answered();
    }

    private Inquiry validateInquiry(Integer inquiryId) {
        return inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("Inquiry not found: " + inquiryId));
    }

    /**
     * 문의글 검색 및 페이징
     *
     * @param pageable 페이징 조건
     * @param keyword 검색 키워드
     * @return 필터링 결과
     */
    @Override
    public InquiryPagingRes list(Pageable pageable, String keyword) {
        // 검색 키워드가 존재하면 검색어를 포함한 결과 리턴, 그 외에는 페이징 결과만 조회
        Page<Inquiry> inquiries;
        if (StringUtils.hasText(keyword)){
            inquiries = searchList(keyword, pageable);
        } else {
            inquiries = index(pageable);
        }
        return InquiryPagingRes.of(inquiries);
    }

    private Page<Inquiry> index(Pageable pageable) {
        return inquiryRepository.findAll(pageable);
    }

    private Page<Inquiry> searchList(String keyword, Pageable pageable) {
        return inquiryRepository.findInquiresByTitleContainingAndContentContaining(keyword, keyword, pageable);
    }
}
