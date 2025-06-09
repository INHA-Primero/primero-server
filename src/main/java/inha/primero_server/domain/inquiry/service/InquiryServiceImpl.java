package inha.primero_server.domain.inquiry.service;

import inha.primero_server.domain.inquiry.dto.request.InquiryRequest;
import inha.primero_server.domain.inquiry.dto.response.InquiryPagingResponse;
import inha.primero_server.domain.inquiry.dto.response.InquiryResponse;
import inha.primero_server.domain.inquiry.entity.Inquiry;
import inha.primero_server.domain.inquiry.mapper.InquiryMapper;
import inha.primero_server.domain.inquiry.repository.InquiryRepository;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
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
    private final InquiryMapper inquiryMapper;

    /**
     * 문의글 작성
     *
     * @param inquiryRequest 사용자 문의글 작성 요청
     * @return 문의글 작성 응답
     */
    @Override
    public InquiryResponse createInquiry(InquiryRequest inquiryRequest, Long userId) { //추후 AuthenticationPrincipal 추가
        // 1. 사용자 검증
        User user = validateUser(userId);

        // 2. Inquiry 생성 및 저장
        Inquiry inquiry = new Inquiry(inquiryRequest.title(), inquiryRequest.content(), user);
        inquiryRepository.save(inquiry);

        return inquiryMapper.toInquiryResponse(inquiry);
    }

    private User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User Not Found : " + userId));
    }

    /**
     * 단일 문의글 조회
     *
     * @param inquiryId 문의글 식별자
     * @return 문의글 조회 응답
     */
    @Override
    public InquiryResponse getInquiry(Integer inquiryId) {
        Inquiry inquiry = validateInquiry(inquiryId);
        return inquiryMapper.toInquiryResponse(inquiry);
    }

    /**
     * 단일 문의글 수정
     *
     * @param inquiryId 문의글 식별자
     * @param inquiryRequest 문의글 수정 요청
     */
    @Override
    public void updateInquiry(Integer inquiryId, InquiryRequest inquiryRequest, Long userId) {
        Inquiry inquiry = validateInquiry(inquiryId);

        if(!inquiry.getUser().getUserId().equals(userId)){
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        inquiry.update(inquiryRequest.title(), inquiryRequest.content());
    }

    /**
     * 단일 문의글 삭제
     * @param inquiryId 문의글 식별자
     */
    @Override
    public void deleteInquiry(Integer inquiryId) {
        Inquiry inquiry = validateInquiry(inquiryId);
        inquiryRepository.delete(inquiry);
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
    public InquiryPagingResponse list(Pageable pageable, String keyword) {

        // 1) 첫 조회
        Page<Inquiry> page = getInquiryPage(pageable, keyword);

        // 2) 클램프 처리
        int req = pageable.getPageNumber();
        int total = page.getTotalPages();
        int lastIndex = Math.max(0, total - 1);
        if (req > lastIndex) {
            pageable = PageRequest.of(lastIndex, pageable.getPageSize(), pageable.getSort());
            page = getInquiryPage(pageable, keyword);
        }

        List<InquiryResponse> inquiryResponseList = inquiryMapper.toInquiryResponseList(page.getContent());

        total = Math.max(1, page.getTotalPages());
        int current = Math.min(total, page.getNumber() + 1);
        int prev = Math.max(1, current - 1);
        int next = Math.min(total, current + 1);

        return new InquiryPagingResponse(inquiryResponseList, prev, next, current, total);
    }

    private Page<Inquiry> getInquiryPage(Pageable pageable, String keyword) {
        return StringUtils.hasText(keyword)
                ? inquiryRepository.findInquiresByTitleContainingOrContentContaining(keyword, keyword, pageable)
                : inquiryRepository.findAll(pageable);
    }

    @Override
    public Page<InquiryResponse> getMyInquires(Long userId, Pageable pageable) {
        User user = validateUser(userId);
        return inquiryRepository.findAllByUser(user, pageable)
                .map(inquiryMapper::toInquiryResponse);
    }
}
