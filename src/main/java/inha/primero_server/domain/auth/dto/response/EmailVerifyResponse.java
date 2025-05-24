package inha.primero_server.domain.auth.dto.response;

public record EmailVerifyResponse(
        boolean success,
        String message
) {}