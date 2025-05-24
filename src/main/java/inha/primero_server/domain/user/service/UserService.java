package inha.primero_server.domain.user.service;

import inha.primero_server.domain.user.dto.request.UserUpdateRequest;
import inha.primero_server.domain.user.dto.response.UserResponse;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import inha.primero_server.global.security.service.TokenService;
import inha.primero_server.global.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final FileUtils fileUtils;

    /**
     * UUID로 사용자 정보 조회
     */
    public UserResponse getUserInfo(String bearerToken) {
        User user = getUserFromToken(bearerToken);
        return UserResponse.from(user);
    }

    /**
     * 사용자 정보 수정
     */
    @Transactional
    public UserResponse updateUser(String bearerToken, UserUpdateRequest request) {
        return updateUser(bearerToken, request, null);
    }

    /**
     * 사용자 정보 수정
     */
    @Transactional
    public UserResponse updateUser(String bearerToken, UserUpdateRequest request, MultipartFile profileImage) {
        User user = getUserFromToken(bearerToken);

        if (request.getPassword() != null) {
            user.updatePassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getNickname() != null) {
            validateNickname(request.getNickname(), user.getNickname());
            user.updateNickname(request.getNickname());
        }
        if (profileImage != null && !profileImage.isEmpty()) {
            if (user.getProfileImageUrl() != null) {
                fileUtils.deleteImage(user.getProfileImageUrl());
            }
            String profileImageUrl = fileUtils.uploadImage(profileImage);
            user.updateProfileImage(profileImageUrl);
        }

        return UserResponse.from(user);
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void deleteUser(String bearerToken) {
        User user = getUserFromToken(bearerToken);
        if (user.getProfileImageUrl() != null) {
            fileUtils.deleteImage(user.getProfileImageUrl());
        }
        userRepository.delete(user);
    }

    /**
     * UUID로 사용자 조회
     */
    private User findUserByUuidOrThrow(String uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));
    }

    private User getUserFromToken(String bearerToken) {
        String userUuid = tokenService.validateAndGetUserUuid(bearerToken);
        return findUserByUuidOrThrow(userUuid);
    }

    private void validateNickname(String nickname, String currentNickname) {
        if (!nickname.equals(currentNickname) && userRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 사용 중인 닉네임입니다.");
        }
    }
}
