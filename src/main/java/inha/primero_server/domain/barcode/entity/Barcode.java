package inha.primero_server.domain.barcode.entity;

import inha.primero_server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Barcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(unique = true, nullable = false)
    private String barcode;

    private LocalDateTime createdAt;

    public Barcode(User user, String barcode) {
        this.user = user;
        this.barcode = barcode;
        this.createdAt = LocalDateTime.now();
    }

    public Long getUserId() {
        return user.getUserId();
    }
}
