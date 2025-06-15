package inha.primero_server.domain.barcode.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class AuthLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private boolean success;
    private String location;
    private LocalDateTime timestamp;
    private String imagePath;

    @Builder
    public AuthLog(Long userId, boolean success, String location, LocalDateTime timestamp, String imagePath) {
        this.userId = userId;
        this.success = success;
        this.location = location;
        this.timestamp = timestamp;
        this.imagePath = imagePath;
    }
}
