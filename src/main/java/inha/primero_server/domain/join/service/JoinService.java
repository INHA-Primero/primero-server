package inha.primero_server.domain.join.service;

import inha.primero_server.domain.join.dto.request.SignUpRequest;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String DEFAULT_PROFILE_IMAGE_URL = "/images/default-profile.png";

    @Transactional
    public User signUp(SignUpRequest request) {
        validateSignUpRequest(request);

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .studentId(request.getStudentId())
                .nickname(request.getNickname())
                .deviceUuid(request.getDeviceUuid())
                .profileImageUrl(DEFAULT_PROFILE_IMAGE_URL)
                .build();

        return userRepository.save(user);
    }

    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean checkStudentIdDuplicate(String studentId) {
        return userRepository.existsByStudentId(studentId);
    }

    private void validateSignUpRequest(SignUpRequest request) {
        if (checkEmailDuplicate(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 사용 중인 이메일입니다.");
        }
        if (checkNicknameDuplicate(request.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 사용 중인 닉네임입니다.");
        }
        if (checkStudentIdDuplicate(request.getStudentId())) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 가입된 학번입니다.");
        }
    }
}
