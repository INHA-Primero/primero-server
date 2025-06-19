package inha.primero_server.domain.recycle.entity;

import inha.primero_server.domain.bin.entity.Bin;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "recycle_tb")
public class Recycle extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bin_id", nullable = false)
    private Bin bin;

    @Column(name = "record_img_path", nullable = false)
    @Setter
    private String recordImgPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Material material;

    @Column(name = "reward_point", nullable = false)
    private Integer rewardPoint;

    @Column(nullable = false)
    private Boolean result;

    @Column(name = "taken_at", nullable = false)
    private LocalDateTime takenAt;

    public Recycle(User user, Bin bin, String recordImgPath, Material material, Integer rewardPoint, Boolean result) {
        this.user = user;
        this.bin = bin;
        this.recordImgPath = recordImgPath;
        this.material = material;
        this.rewardPoint = rewardPoint;
        this.result = result;
        this.takenAt = LocalDateTime.now();
    }
} 