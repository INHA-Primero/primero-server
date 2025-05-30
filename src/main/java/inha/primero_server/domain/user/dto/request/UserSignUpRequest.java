package inha.primero_server.domain.user.dto.request;

import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequest {
    @NotBlank(message = "이름은 필수 항목입니다.")
    String name;

    @NotNull(message = "학번은 필수 항목입니다.")
    Integer studentNumber;
    @NotBlank(message = "나무이름은 필수 항목입니다.")
    String treeName;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email
    String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8)
    String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    @Size(min = 8)
    String confirmPassword;

    @NotBlank
    private String deviceUuid;
}