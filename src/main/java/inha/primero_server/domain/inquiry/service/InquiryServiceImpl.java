package inha.primero_server.domain.inquiry.service;

import inha.primero_server.domain.inquiry.dto.request.InquiryReq;
import inha.primero_server.domain.inquiry.dto.response.InquiryRes;
import inha.primero_server.domain.inquiry.entity.Inquiry;
import inha.primero_server.domain.inquiry.entity.User;
import inha.primero_server.domain.inquiry.entity.enums.Status;
import inha.primero_server.domain.inquiry.repository.InquiryRepository;
import inha.primero_server.domain.inquiry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService{

    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    //추후 @AuthenticationPrincial 추가
    @Override
    public InquiryRes createInquiry(InquiryReq inquiryReq, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));
        inquiryReq.setUser(user);
        Inquiry inquiry = inquiryReq.toEntity();
        inquiryRepository.save(inquiry);
        return new InquiryRes(inquiry);
    }

    @Override
    @Transactional(readOnly = true)
    public InquiryRes findById(Integer id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회 실패"));
        return new InquiryRes(inquiry);
    }

    @Override
    @Transactional
    public void updateInquiry(Integer id, InquiryReq inquiryReq) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다. id= " + id));
        inquiry.update(inquiryReq);
    }

    @Override
    @Transactional
    public void deleteInquiry(Integer id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다. id= " + id));
        inquiryRepository.deleteById(id);
    }

    @Override
    public Page<Inquiry> index(Pageable pageable) {
        return inquiryRepository.findAll(pageable);
    }
}
