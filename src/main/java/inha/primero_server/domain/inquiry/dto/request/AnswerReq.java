package inha.primero_server.domain.inquiry.dto.request;

import inha.primero_server.domain.inquiry.entity.Answer;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Getter
public class AnswerReq {
    @NotBlank
    private String content;

    public Answer toEntity(){
        return Answer.builder()
                .content(content)
                .build();
    }
}
