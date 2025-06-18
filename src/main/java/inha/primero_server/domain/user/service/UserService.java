package inha.primero_server.domain.user.service;

import inha.primero_server.domain.barcode.service.BarcodeService;
import inha.primero_server.domain.character.entity.Character;
import inha.primero_server.domain.character.repository.CharacterRepository;
import inha.primero_server.domain.user.dto.request.UserSignUpRequest;
import inha.primero_server.domain.user.dto.request.UserModifyRequest;
import inha.primero_server.domain.user.dto.response.UserResponse;
import inha.primero_server.domain.user.dto.response.UserInfoResponse;
import inha.primero_server.domain.user.dto.response.UserRankingResponse;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.entity.Status;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;
    private final BarcodeService barcodeService;
    private final CharacterRepository characterRepository;

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

        Character character = Character.create(user, request.getTreeName());

        characterRepository.save(character);

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


    @Bean
    public CommandLineRunner createTestUser() {
        return args -> {
            String email = "test@inha.edu";

            if (userRepository.findByEmail(email).isEmpty()) {
                User user = User.create(
                        email,
                        "테스트계정",
                        20250616,
                        "테스트나무",
                        passwordEncoder.encode("test1234"),
                        "device-uuid-1234"
                );

                userRepository.save(user);
                barcodeService.generateFor(user);
                System.out.println("테스트 계정 생성 완료: " + email);
            } else {
                System.out.println("테스트 계정 이미 존재: " + email);
            }
        };
    }

    public UserRankingResponse getUserRanking(Long userId) {
        User user = userRepository.findByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));

        List<User> allUsers = userRepository.findAll().stream()
                .filter(u -> u.getStatus() == Status.ACTIVE)
                .sorted((u1, u2) -> u2.getTotalPoint().compareTo(u1.getTotalPoint()))
                .collect(Collectors.toList());

        long ranking = allUsers.indexOf(user) + 1;

        return UserRankingResponse.builder()
                .nickname(user.getTreeName())
                .ranking(ranking)
                .totalPoint(user.getTotalPoint())
                .build();
    }

    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));

        return UserInfoResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .studentNumber(user.getStudentNumber())
                .nickname(user.getTreeName())
                .profileImgPath(user.getProfileImgPath())
                .totalPoint(user.getTotalPoint())
                .build();
    }
}
