package inha.primero_server.domain.user.controller;

import inha.primero_server.domain.user.dto.request.UserSignUpRequest;
import inha.primero_server.domain.user.dto.request.UserModifyRequest;
import inha.primero_server.domain.user.dto.response.UserResponse;
import inha.primero_server.domain.user.service.UserService;
import inha.primero_server.global.common.JwtUtil;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody UserSignUpRequest req){
        userService.signup(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserModifyRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
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
