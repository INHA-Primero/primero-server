package inha.primero_server.domain.inquiry.service;

import inha.primero_server.domain.inquiry.entity.Inquiry;
import inha.primero_server.domain.inquiry.entity.enums.Status;
import inha.primero_server.domain.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public void saveInquiry(){
        for(int i = 0; i < 10; i++){
            Inquiry inquiry = Inquiry.builder()
                    .title("Hi" + i)
                    .content("World!" + i)
                    .status(Status.OPEN)
                    .build();
            inquiryRepository.save(inquiry);
        }
    }
}
