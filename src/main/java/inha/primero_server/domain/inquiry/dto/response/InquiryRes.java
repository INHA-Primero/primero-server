package inha.primero_server.domain.inquiry.dto.response;

import inha.primero_server.domain.inquiry.entity.Inquiry;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InquiryRes {
    private final Integer id;
    private final String title;
    private final String content;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;
    private final Integer userId;

    public InquiryRes(Inquiry inquiry) {
        this.id = inquiry.getId();
        this.title = inquiry.getTitle();
        this.content = inquiry.getContent();
        this.status = inquiry.getStatus().name();
        this.createdAt = inquiry.getCreatedAt();
        this.lastModifiedAt = inquiry.getLastModifiedAt();
        this.userId = inquiry.getUser().getId();
    }
}
