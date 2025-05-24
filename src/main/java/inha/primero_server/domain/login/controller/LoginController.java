package inha.primero_server.domain.login.controller;

import inha.primero_server.domain.login.dto.request.LoginRequest;
import inha.primero_server.domain.login.dto.response.LoginResponse;
import inha.primero_server.domain.login.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = loginService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "로그인 성공",
                "status_code", HttpStatus.OK.value(),
                "user_id", response.getUserId(),
                "nickname", response.getNickname(),
                "role", response.getRole(),
                "total_point", response.getTotalPoint(),
                "barcode_url", response.getBarcodeUrl()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        loginService.logout(token);
        return ResponseEntity.ok().build();
    }
}