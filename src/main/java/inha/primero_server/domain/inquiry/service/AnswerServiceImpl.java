package inha.primero_server.domain.inquiry.service;

import inha.primero_server.domain.inquiry.dto.request.AnswerRequest;
import inha.primero_server.domain.inquiry.dto.response.AnswerResponse;
import inha.primero_server.domain.inquiry.entity.Answer;
import inha.primero_server.domain.inquiry.entity.Inquiry;
import inha.primero_server.domain.inquiry.mapper.InquiryMapper;
import inha.primero_server.domain.inquiry.repository.AnswerRepository;
import inha.primero_server.domain.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final InquiryMapper inquiryMapper;

    /**
     * 답변 작성
     *
     * @param inquiryId 문의글 식별자
     * @param answerRequest 답변 작성 요청
     * @return 작성 내용 응답
     */
    @Override
    public AnswerResponse createAnswer(Integer inquiryId, AnswerRequest answerRequest) {
        Inquiry inquiry = getInquiry(inquiryId);

        Answer answer = new Answer(inquiry, answerRequest.content());
        inquiry.answered();
        Answer saved = answerRepository.save(answer);
        return inquiryMapper.toAnswerResponse(saved);
    }

    private Inquiry getInquiry(Integer inquiryId) {
        return inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new NoSuchElementException("Inquiry not found: " + inquiryId));
    }

    /**
     * 답변 수정
     *
     * @param answerId 답변 식별자
     * @param answerRequest 답변 요청
     * @return 답변 수정 응답 반환
     */
    @Override
    public AnswerResponse updateAnswer(Integer answerId, AnswerRequest answerRequest) {
        Answer answer = getAnswer(answerId);

        answer.updateContent(answerRequest.content());
        return inquiryMapper.toAnswerResponse(answer);
    }

    private Answer getAnswer(Integer answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new NoSuchElementException("Answer not found: " + answerId));
    }

    /**
     * 답변 삭제
     *
     * @param answerId 답변 식별자
     * @param inquiryId 문의 식별자
     * @return 삭제 답변 응답 반환
     */
    @Override
    public AnswerResponse deleteAnswer(Integer answerId, Integer inquiryId) {
        Answer answer = getAnswer(answerId);
        Inquiry inquiry = getInquiry(inquiryId);

        inquiry.getAnswers().remove(answer);

        if (inquiry.getAnswers().isEmpty()) inquiry.opened();
        return inquiryMapper.toAnswerResponse(answer);
    }
}
