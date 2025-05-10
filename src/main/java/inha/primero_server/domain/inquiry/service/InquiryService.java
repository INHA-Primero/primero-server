package inha.primero_server.domain.inquiry.service;

import inha.primero_server.domain.inquiry.dto.request.InquiryReq;
import inha.primero_server.domain.inquiry.dto.response.InquiryRes;
import inha.primero_server.domain.inquiry.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InquiryService {
    public InquiryRes createInquiry(InquiryReq inquiryReq, String username);
    public InquiryRes findById(Integer id);
    public void updateInquiry(Integer id, InquiryReq inquiryReq);
    public void deleteInquiry(Integer id);
    public Page<Inquiry> index(Pageable pageable);
}
