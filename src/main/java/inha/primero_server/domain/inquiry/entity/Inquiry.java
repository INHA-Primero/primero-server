package inha.primero_server.domain.inquiry.entity;

import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Inquiry 엔티티는 애플리케이션의 문의글 정보를 나타냄.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean isAnswered;

    @Column(columnDefinition = "TEXT")
    private String answer;

    private LocalDateTime answeredAt;

    @Builder
    public Inquiry(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.isAnswered = false;
        this.addToUser();
    }

    public void answer(String answer) {
        this.answer = answer;
        this.isAnswered = true;
        this.answeredAt = LocalDateTime.now();
    }

    private void addToUser() {
        if (this.user != null) {
            user.getInquiryList().add(this);
        }
    }
}
