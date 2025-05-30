package inha.primero_server.domain.auth;

import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailAuthService {
    private final StringRedisTemplate redisTemplate;

    public void sendCodeToEmail(String email){
        if(!email.endsWith("@inha.edu")){
            throw new CustomException(ErrorCode.INVALID_PARAMETER, "인하대학교 이메일만 가입 가능합니다.");
        }

        String code = String.format("%06d", new Random().nextInt(1000000));
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);

        System.out.println("[테스트용] 인증코드 : " + code);
        // 실제 이메일 발송 로직 연결 해야 함
    }

    public void verifyCode(String email, String code){
        String stored = redisTemplate.opsForValue().get(email);
        if(stored == null || !stored.equals(code)){
            throw new CustomException(ErrorCode.UNAUTHORIZED, "인증번호가 올바르지 않습니다.");
        }
        redisTemplate.opsForValue().set(email, "VERIFIED", 10, TimeUnit.MINUTES);
    }
}
