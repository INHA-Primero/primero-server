package inha.primero_server.domain.user.entity;

import inha.primero_server.domain.inquiry.entity.Inquiry;
import inha.primero_server.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(unique = true)
    private String deviceUuid;

    @Column(nullable = false)
    private boolean emailVerified;

    @Column(nullable = false)
    private String profileImageUrl;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int expGauge;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inquiry> inquiryList = new ArrayList<>();

    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        uuid = UUID.randomUUID().toString();
        barcode = generateBarcode();
    }

    private String generateBarcode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Builder
    public User(String email, String password, String name, String studentId, String nickname, String profileImageUrl, String deviceUuid) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.studentId = studentId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.deviceUuid = deviceUuid;
        this.level = 1;
        this.expGauge = 0;
        this.emailVerified = false;
    }

    public void verifyEmail() {
        this.emailVerified = true;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void login(String deviceUuid) {
        this.deviceUuid = deviceUuid;
        this.lastLoginAt = LocalDateTime.now();
    }

    public void logout() {
        this.deviceUuid = null;
    }

    public boolean isLoggedIn() {
        return this.deviceUuid != null;
    }

    public boolean isSameDevice(String deviceUuid) {
        return this.deviceUuid != null && this.deviceUuid.equals(deviceUuid);
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void gainExp(int exp) {
        this.expGauge += exp;
        while (this.expGauge >= 100) {
            this.level++;
            this.expGauge -= 100;
        }
    }
}