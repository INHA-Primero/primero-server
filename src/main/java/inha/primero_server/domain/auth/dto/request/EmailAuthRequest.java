package inha.primero_server.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailAuthRequest(
        @Email @NotBlank String email
) {
    public EmailAuthRequest {
        if (!email.endsWith("@inha.edu")) {
            throw new IllegalArgumentException("inha.edu 이메일만 사용 가능합니다.");
        }
    }
}