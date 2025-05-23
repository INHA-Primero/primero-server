package inha.primero_server.domain.user.controller;

import inha.primero_server.domain.user.dto.request.UserSignUpRequest;
import inha.primero_server.domain.user.dto.request.UserModifyRequest;
import inha.primero_server.domain.user.dto.response.UserResponse;
import inha.primero_server.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(
            @RequestBody @Valid UserSignUpRequest request,
            @RequestHeader("X-DEVICE-UUID") String deviceUuid // FE에서 header로 넘겨줘야함
    ) {
        Long userId = userService.signUp(request, deviceUuid);
        return ResponseEntity.ok(userId);
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
}
