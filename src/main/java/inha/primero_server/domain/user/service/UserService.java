package inha.primero_server.domain.user.service;

import inha.primero_server.domain.user.dto.request.UserSignUpRequest;
import inha.primero_server.domain.user.dto.request.UserModifyRequest;
import inha.primero_server.domain.user.dto.response.UserResponse;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.entity.Status;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long signUp(UserSignUpRequest request, String deviceUuid) {
        validateDuplicate(request.email(), request.studentNumber(), request.nickname(), deviceUuid);

        User user = User.create(
                request.email(),
                request.name(), // name 추가
                request.studentNumber(),
                request.nickname(),
                request.password(),
                deviceUuid
        );

        return userRepository.save(user).getUserId();
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserModifyRequest request) {
        User user = getActiveUser(userId);

        String password = (request.password() == null || request.password().isBlank())
                ? user.getPassword()
                : request.password();

        String profileImgPath = (request.profileImgPath() == null || request.profileImgPath().isBlank())
                ? user.getProfileImgPath()
                : request.profileImgPath();

        user.updateInfo(request.nickname(), password, profileImgPath);
        return UserResponse.of(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = getActiveUser(userId);
        user.delete();
    }

    public UserResponse getUser(Long userId) {
        User user = getActiveUser(userId);
        return UserResponse.of(user);
    }

    private void validateDuplicate(String email, int studentNumber, String nickname, String deviceUuid) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 등록된 이메일입니다.");
        }
        if (userRepository.existsByStudentNumber(studentNumber)) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 등록된 학번입니다.");
        }
        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 사용 중인 닉네임입니다.");
        }
        if (userRepository.existsByDeviceUuid(deviceUuid)) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 등록된 기기입니다.");
        }
    }

    private User getActiveUser(Long userId) {
        return userRepository.findByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }
}
