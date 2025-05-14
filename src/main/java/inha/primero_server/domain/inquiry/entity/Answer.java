package inha.primero_server.domain.inquiry.entity;

import inha.primero_server.domain.inquiry.dto.request.AnswerReq;
import inha.primero_server.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Answer 엔티티는 애플리케이션의 답변 정보를 나타냄.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "answer_tb")
public class Answer extends BaseEntity {

    @Id
    @Column(name = "answer_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id", nullable = false)
    private Inquiry inquiry;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
        inquiry.getAnswers().add(this);
    }

    public void update(AnswerReq req) {
        this.content = req.getContent();
    }
}
