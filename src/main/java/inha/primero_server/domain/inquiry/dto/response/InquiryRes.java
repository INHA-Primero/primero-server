package inha.primero_server.domain.inquiry.dto.response;

import inha.primero_server.domain.inquiry.entity.Inquiry;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class InquiryRes {
    private final String title;
    private final String content;
    private final String status;
    private final String writer;
    private final List<AnswerRes> answers;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;

    public InquiryRes(Inquiry inquiry) {
        this.title = inquiry.getTitle();
        this.content = inquiry.getContent();
        this.status = inquiry.getStatus().getLabel();
        this.writer = inquiry.getWriter();
        this.createdAt = inquiry.getCreatedAt();
        this.lastModifiedAt = inquiry.getLastModifiedAt();
        this.answers = Optional.ofNullable(inquiry.getAnswers())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(AnswerRes::new)
                .collect(Collectors.toList());
    }
}
