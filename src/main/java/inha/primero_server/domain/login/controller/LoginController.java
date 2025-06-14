package inha.primero_server.domain.login.controller;

import inha.primero_server.domain.login.dto.request.LoginReq;
import inha.primero_server.domain.login.dto.response.LoginRes;
import inha.primero_server.domain.login.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginRes> login(@RequestBody @Valid LoginReq request) {
        LoginRes loginRes = loginService.login(request);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + loginRes.token())
                .body(loginRes);
    }

}
