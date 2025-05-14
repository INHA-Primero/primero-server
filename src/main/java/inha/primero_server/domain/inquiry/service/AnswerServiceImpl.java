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

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final InquiryRepository inquiryRepository;

    @Override
    public AnswerRes create(Integer inquiryId, AnswerReq answerReq) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new NoSuchElementException("Inquiry not found: " + inquiryId));
        Answer answer = answerReq.toEntity();
        answer.setInquiry(inquiry);
        answerRepository.save(answer);
        return new AnswerRes(answer);
    }

    @Override
    public List<AnswerRes> findByInquiry(Integer inquiryId) {
        return List.of();
    }
}
