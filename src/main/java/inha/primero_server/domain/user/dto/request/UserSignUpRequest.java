package inha.primero_server.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignUpRequest {
    @NotBlank(message = "이름은 필수 항목입니다.")
    @Schema(description = "사용자 이름", example = "김철수")
    String name;

    @NotNull(message = "학번은 필수 항목입니다.")
    @Schema(description = "학번", example = "12252222")
    Integer studentNumber;
    @NotBlank(message = "나무이름은 필수 항목입니다.")
    @Schema(description = "나무 이름", example = "나문이")
    String treeName;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email
    @Schema(description = "이메일", example = "chulsoo@inha.edu")
    String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8)
    @Schema(description = "비밀번호, 최소8자 이상", example = "12345678")
    String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    @Size(min = 8)
    @Schema(description = "비밀번호 재확인", example = "12345678")
    String confirmPassword;

    @NotBlank
    String deviceUuid;

    Integer totalPoint = 0;
}