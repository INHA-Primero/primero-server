package inha.primero_server.domain.inquiry.service;

import inha.primero_server.domain.inquiry.dto.request.InquiryReq;
import inha.primero_server.domain.inquiry.dto.response.InquiryPagingRes;
import inha.primero_server.domain.inquiry.dto.response.InquiryRes;
import inha.primero_server.domain.inquiry.entity.User;
import org.springframework.data.domain.Pageable;

public interface InquiryService {
    //InquiryRes createInquiry(InquiryReq inquiryReq, User user);
    InquiryRes createInquiry(InquiryReq inquiryReq, Integer userId);

    InquiryRes getInquiry(Integer id);

    void updateInquiry(Integer id, InquiryReq inquiryReq);

    void deleteInquiry(Integer id);

    void markAnswered(Integer id);

    InquiryPagingRes list(Pageable pageable, String keyword);
}
