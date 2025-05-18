package inha.primero_server.domain.user.dto.request;

import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserSignUpRequest(

        @Email
        @NotBlank(message = "이메일은 필수 항목입니다.")
        String email,

        @NotBlank(message = "이름은 필수 항목입니다.")
        String name,

        @NotNull(message = "학번은 필수 항목입니다.")
        Integer studentNumber,

        @NotBlank(message = "닉네임은 필수 항목입니다.")
        String nickname,

        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        String password,

        @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
        String confirmPassword

) {
    public UserSignUpRequest {
        if (!password.equals(confirmPassword)) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH, "비밀번호가 일치하지 않습니다.");
        }
    }
}
