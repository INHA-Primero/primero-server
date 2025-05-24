package inha.primero_server.domain.auth.service;

import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;
    private static final long VERIFICATION_CODE_TTL = 5; // 5분

    public void sendVerificationEmail(String email) {
        String code = generateVerificationCode();
        saveVerificationCode(email, code);
        sendEmail(email, code);
    }

    public boolean verifyCode(String email, String code) {
        String savedCode = redisTemplate.opsForValue().get(getRedisKey(email));
        if (savedCode == null) {
            throw new CustomException(ErrorCode.INVALID_STATE, "인증 코드가 만료되었습니다.");
        }
        return savedCode.equals(code);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    private void saveVerificationCode(String email, String code) {
        redisTemplate.opsForValue().set(
                getRedisKey(email),
                code,
                VERIFICATION_CODE_TTL,
                TimeUnit.MINUTES
        );
    }

    private void sendEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Primero 이메일 인증");
        message.setText("인증 코드: " + code + "\n5분 이내에 입력해주세요.");
        mailSender.send(message);
    }

    private String getRedisKey(String email) {
        return "verification:" + email;
    }
} 