package inha.primero_server.domain.login.service;

import inha.primero_server.domain.login.dto.request.LoginRequest;
import inha.primero_server.domain.login.dto.response.LoginResponse;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import inha.primero_server.global.security.service.TokenService;
import inha.primero_server.global.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "가입되지 않은 이메일입니다."));

        validateLogin(user, request.getPassword(), request.getDeviceUuid());

        String token = tokenService.createToken(user.getUuid(), request.getDeviceUuid());
        user.login(request.getDeviceUuid());

        return LoginResponse.of(token, user);
    }

    @Transactional
    public void logout(String bearerToken) {
        String userUuid = tokenService.validateAndGetUserUuid(bearerToken);
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));

        user.logout();
        tokenService.invalidateToken(TokenUtils.extractToken(bearerToken));
    }

    private void validateLogin(User user, String password, String deviceUuid) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "잘못된 비밀번호입니다.");
        }

        if (!user.isEmailVerified()) {
            throw new CustomException(ErrorCode.INVALID_STATE, "이메일 인증이 필요합니다.");
        }

        if (user.isLoggedIn()) {
            if (!user.isSameDevice(deviceUuid)) {
                throw new CustomException(ErrorCode.INVALID_STATE, "다른 기기에서 이미 로그인되어 있습니다.");
            }
        } else if (user.getDeviceUuid() != null && !user.getDeviceUuid().equals(deviceUuid)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "등록되지 않은 기기입니다.");
        }
    }
}
