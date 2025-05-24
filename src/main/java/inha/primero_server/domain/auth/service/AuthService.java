package inha.primero_server.domain.auth.service;

import inha.primero_server.domain.auth.dto.request.EmailVerificationRequest;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Transactional
    public void verifyEmail(EmailVerificationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "가입되지 않은 이메일입니다."));

        if (user.isEmailVerified()) {
            throw new CustomException(ErrorCode.INVALID_STATE, "이미 인증된 이메일입니다.");
        }

        if (!emailService.verifyCode(request.getEmail(), request.getCode())) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "잘못된 인증 코드입니다.");
        }

        user.verifyEmail();
    }

    public void resendVerificationCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "가입되지 않은 이메일입니다."));

        if (user.isEmailVerified()) {
            throw new CustomException(ErrorCode.INVALID_STATE, "이미 인증된 이메일입니다.");
        }

        emailService.sendVerificationEmail(email);
    }
} 