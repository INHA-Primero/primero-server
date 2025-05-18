package inha.primero_server.domain.inquiry.entity;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "inquiry",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @Builder
    public Inquiry(String title, String content, User user) {
        this.title = title;
        this.content = content;
        setUser(user);
    }

    private void setUser(User user) {
        this.user = user;
        user.getInquiryList().add(this);
    }

    public String getWriter() {
        return this.user.getName();
    }

    public void answered() {
        this.status = Status.ANSWERED;
    }

    public void opened() {
        this.status = Status.OPEN;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
