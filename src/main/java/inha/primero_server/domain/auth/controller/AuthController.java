package inha.primero_server.domain.auth.controller;

import inha.primero_server.domain.auth.dto.request.EmailVerificationRequest;
import inha.primero_server.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyEmail(@Valid @RequestBody EmailVerificationRequest request) {
        authService.verifyEmail(request);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "인증 성공",
                "status_code", HttpStatus.OK.value()
        ));
    }

    @PostMapping("/resend")
    public ResponseEntity<Map<String, Object>> resendVerificationCode(@RequestParam String email) {
        authService.resendVerificationCode(email);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "인증번호가 재전송되었습니다.",
                "status_code", HttpStatus.OK.value()
        ));
    }

}