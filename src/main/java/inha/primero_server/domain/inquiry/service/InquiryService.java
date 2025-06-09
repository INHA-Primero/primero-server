package inha.primero_server.domain.inquiry.service;

import inha.primero_server.domain.inquiry.dto.request.InquiryRequest;
import inha.primero_server.domain.inquiry.dto.response.InquiryPagingResponse;
import inha.primero_server.domain.inquiry.dto.response.InquiryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryService {
    //InquiryRes createInquiry(InquiryReq inquiryReq, User user);
    InquiryResponse createInquiry(InquiryRequest inquiryRequest, Long userId);

    InquiryResponse getInquiry(Integer inquiryId);

    void updateInquiry(Integer inquiryId, InquiryRequest inquiryRequest, Long userId);

    void deleteInquiry(Integer inquiryId);

    InquiryPagingResponse list(Pageable pageable, String keyword);

    Page<InquiryResponse> getMyInquires(Long userId, Pageable pageable);
}
