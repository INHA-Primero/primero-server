package inha.primero_server.domain.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class EmailToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String code;
    private LocalDateTime expiredAt;
    private boolean isVerified = false;

    public static EmailToken create(String email, String code, int minutesToExpire) {
        EmailToken token = new EmailToken();
        token.email = email;
        token.code = code;
        token.expiredAt = LocalDateTime.now().plusMinutes(minutesToExpire);
        return token;
    }

    public void verify(String inputCode) {
        if (!this.code.equals(inputCode) || this.expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증코드가 유효하지 않거나 만료되었습니다.");
        }
        this.isVerified = true;
    }
}
