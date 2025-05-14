package inha.primero_server.domain.user.dto.response;

import inha.primero_server.domain.user.entity.User;

public record UserResponse(
        Long userId,
        String email,
        String name,
        Integer studentNumber,
        String nickname,
        String profileImgPath,
        Integer totalPoint
) {
    public static UserResponse of(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getStudentNumber(),
                user.getNickname(),
                user.getProfileImgPath(),
                user.getTotalPoint()
        );
    }
}
