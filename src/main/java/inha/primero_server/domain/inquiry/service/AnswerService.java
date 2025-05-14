package inha.primero_server.domain.inquiry.service;

import inha.primero_server.domain.inquiry.dto.request.AnswerReq;
import inha.primero_server.domain.inquiry.dto.response.AnswerRes;

import java.util.List;

public interface AnswerService {
    AnswerRes create(Integer inquiryId, AnswerReq req);
    List<AnswerRes> findByInquiry(Integer inquiryId);
}
