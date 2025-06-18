package inha.primero_server.domain.user.controller;

import inha.primero_server.domain.user.dto.request.UserSignUpRequest;
import inha.primero_server.domain.user.dto.request.UserModifyRequest;
import inha.primero_server.domain.user.dto.response.UserResponse;
import inha.primero_server.domain.user.dto.response.UserInfoResponse;
import inha.primero_server.domain.user.dto.response.UserRankingResponse;
import inha.primero_server.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import inha.primero_server.global.common.JwtUtil;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user controller", description = "사용자 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "새로운 사용자를 생성합니다.")
    public ResponseEntity<Void> signup(@Valid @RequestBody UserSignUpRequest req){
        userService.signup(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "사용자 조회 API", description = "사용자를 조회합니다.")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/{userId}")
    @Operation(summary = "사용자 수정 API", description = "사용자의 정보를 수정합니다.")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserModifyRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "사용자 삭제 API", description = "사용자의 정보를 삭제합니다.")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ranking")
    public ResponseEntity<UserRankingResponse> getUserRanking(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(userService.getUserRanking(userId));
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(
            @RequestHeader(value = "Authorization", required = false) String token
    ) {
        if (token == null || token.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_JWT, "Authorization 헤더가 없습니다.");
        }

        Long userId = jwtUtil.getUserId(token);
        return ResponseEntity.ok(userService.getUser(userId));
    }
}
