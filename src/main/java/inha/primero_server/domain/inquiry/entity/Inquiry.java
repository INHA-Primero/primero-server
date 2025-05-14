package inha.primero_server.domain.inquiry.entity;

import inha.primero_server.domain.inquiry.dto.request.InquiryReq;
import inha.primero_server.domain.inquiry.entity.enums.Status;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Inquiry 엔티티는 애플리케이션의 문의글 정보를 나타냄.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inquiry_tb")
public class Inquiry extends BaseEntity {

    @Id
    @Column(name = "inquiry_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();

    public String getWriter() {
        return this.user.getUsername();
    }

    public void setUser(User user) {
        this.user = user;
        user.getInquiryList().add(this);
    }

    public void answered() {
        this.status = Status.ANSWERED;
    }

    public void update(InquiryReq req) {
        this.title = req.getTitle();
        this.content = req.getContent();
    }
}
