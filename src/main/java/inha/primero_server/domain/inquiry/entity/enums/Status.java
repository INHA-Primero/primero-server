package inha.primero_server.domain.inquiry.entity.enums;

import lombok.Getter;

@Getter
public enum Status {
    OPEN("답변 대기 중"),
    ANSWERED("답변 완료"),
    CLOSE("종료됨");

    private final String label;

    Status(String label) {
        this.label = label;
    }
}
