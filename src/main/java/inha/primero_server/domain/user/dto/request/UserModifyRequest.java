package inha.primero_server.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserModifyRequest(

        @NotBlank(message = "닉네임은 필수 항목입니다.")
        String nickname,

        String password,

        String profileImgPath

) {}
