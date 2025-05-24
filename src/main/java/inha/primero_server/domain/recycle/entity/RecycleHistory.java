package inha.primero_server.domain.recycle.entity;

import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecycleHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String barcode;

    @Column(nullable = false)
    private boolean success;

    @Column(nullable = false)
    private int earnedExp;

    private String failReason;

    @Builder
    public RecycleHistory(User user, String barcode, boolean success, int earnedExp, String failReason) {
        this.user = user;
        this.barcode = barcode;
        this.success = success;
        this.earnedExp = earnedExp;
        this.failReason = failReason;
    }

    public static RecycleHistory createSuccessHistory(User user, String barcode, int earnedExp) {
        return RecycleHistory.builder()
                .user(user)
                .barcode(barcode)
                .success(true)
                .earnedExp(earnedExp)
                .build();
    }

    public static RecycleHistory createFailHistory(User user, String barcode, String reason) {
        return RecycleHistory.builder()
                .user(user)
                .barcode(barcode)
                .success(false)
                .earnedExp(0)
                .failReason(reason)
                .build();
    }
} 