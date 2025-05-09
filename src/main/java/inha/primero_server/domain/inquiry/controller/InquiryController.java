package inha.primero_server.domain.inquiry.controller;

import inha.primero_server.domain.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping("/test")
    public String test() {
        inquiryService.saveInquiry();
        return "succeed";
    }
}
