package inha.primero_server.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserModifyRequest(

        @NotBlank(message = "나무이름은 필수 항목입니다.")
        String treeName,
        String password,
        String profileImgPath

) {}
