package inha.primero_server.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 8자 이상의 영문, 숫자, 특수문자를 포함해야 합니다.")
    private String password;  // 변경하지 않을 경우 null

    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", 
            message = "닉네임은 2~10자의 한글, 영문, 숫자만 사용 가능합니다.")
    private String nickname;  // 변경하지 않을 경우 null
} 