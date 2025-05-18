package inha.primero_server.domain.inquiry.service;

import inha.primero_server.domain.inquiry.dto.request.AnswerRequest;
import inha.primero_server.domain.inquiry.dto.response.AnswerResponse;

public interface AnswerService {
    AnswerResponse createAnswer(Integer inquiryId, AnswerRequest answerRequest);
    AnswerResponse updateAnswer(Integer inquiryId, AnswerRequest answerRequest);
    AnswerResponse deleteAnswer(Integer answerId, Integer inquiryId);
}
