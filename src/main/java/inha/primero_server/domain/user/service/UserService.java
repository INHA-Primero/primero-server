package inha.primero_server.domain.user.service;

import inha.primero_server.domain.barcode.service.BarcodeService;
import inha.primero_server.domain.user.dto.request.UserSignUpRequest;
import inha.primero_server.domain.user.dto.request.UserModifyRequest;
import inha.primero_server.domain.user.dto.response.UserResponse;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.entity.Status;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;
    private final BarcodeService barcodeService;

    @Transactional
    public void signup(UserSignUpRequest request) {
        if (!request.getEmail().endsWith("@inha.edu")) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER, "인하대학교 이메일만 가입할 수 있습니다.");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH, "비밀번호가 일치하지 않습니다.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 가입된 이메일입니다.");
        }

        // 이메일 인증은 테스트 시 주석 처리
        // String verified = redisTemplate.opsForValue().get(request.getEmail());
        // if (!"VERIFIED".equals(verified)) {
        //     throw new CustomException(ErrorCode.UNAUTHORIZED, "이메일 인증이 필요합니다.");
        // }

        if (userRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 등록된 학번입니다.");
        }

        if (userRepository.existsByDeviceUuid(request.getDeviceUuid())) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "해당 기기는 이미 가입되어 있습니다.");
        }

        User user = User.create(
                request.getEmail(),
                request.getName(),
                request.getStudentNumber(),
                request.getTreeName(),
                passwordEncoder.encode(request.getPassword()),
                request.getDeviceUuid()
        );

        userRepository.save(user);

        barcodeService.generateFor(user);
    }

    public UserResponse getUser(Long userId) {
        User user = userRepository.findByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));
        return UserResponse.of(user);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserModifyRequest request) {
        User user = userRepository.findByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));

        if (request.password() != null) {
            user.encodePassword(passwordEncoder.encode(request.password()));
        }

        user.updateInfo(request.treeName(), user.getPassword(), request.profileImgPath());
        return UserResponse.of(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));
        user.delete();
    }
}
