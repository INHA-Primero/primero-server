package inha.primero_server.domain.inquiry.service;

import inha.primero_server.domain.inquiry.dto.request.AnswerReq;
import inha.primero_server.domain.inquiry.dto.response.AnswerRes;
import inha.primero_server.domain.inquiry.entity.Answer;
import inha.primero_server.domain.inquiry.entity.Inquiry;
import inha.primero_server.domain.inquiry.repository.AnswerRepository;
import inha.primero_server.domain.inquiry.repository.InquiryRepository;
import inha.primero_server.domain.inquiry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * UserServiceImpl : 답변 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final InquiryRepository inquiryRepository;

    /**
     * 답변 작성
     *
     * @param inquiryId 문의글 식별자
     * @param answerReq 답변 작성 요청
     * @return 작성 내용 응답
     */
    @Override
    public AnswerRes create(Integer inquiryId, AnswerReq answerReq) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new NoSuchElementException("Inquiry not found: " + inquiryId));
        Answer answer = answerReq.toEntity();
        answer.setInquiry(inquiry);
        answerRepository.save(answer);
        return new AnswerRes(answer);
    }
}
