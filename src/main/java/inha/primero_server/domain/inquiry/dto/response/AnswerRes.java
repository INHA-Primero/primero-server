package inha.primero_server.domain.inquiry.dto.response;

import inha.primero_server.domain.inquiry.entity.Answer;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AnswerRes {
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;

    public AnswerRes(Answer answer) {
        this.content = answer.getContent();
        this.createdAt = answer.getCreatedAt();
        this.lastModifiedAt = answer.getLastModifiedAt();
    }
}
