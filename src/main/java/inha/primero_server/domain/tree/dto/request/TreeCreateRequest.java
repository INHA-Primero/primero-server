package inha.primero_server.domain.tree.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record TreeCreateRequest (
    @Schema(description = "위도", example = "41.33888")
    @NotNull(message = "위도는 필수 기입해야합니다.")
    double latitude,
    @Schema(description = "경도", example = ", 69.33731")
    @NotNull(message = "경도는 필수 기입해야합니다.")
    double longitude,
    @Schema(description = "이메일", example = "chulsoo@inha.edu")
    @Email(message = "이메일은 필수 기입해야합니다.")
    String email
){}