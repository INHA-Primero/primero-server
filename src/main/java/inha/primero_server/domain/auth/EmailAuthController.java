package inha.primero_server.domain.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmailAuthController {
    private final EmailAuthService emailAuthService;

    @PostMapping("/send")
    public void send(@RequestBody @Valid EmailRequest request) {
        emailAuthService.sendCodeToEmail(request.email());
    }

    @PostMapping("/verify")
    public void verify(@RequestBody @Valid VerifyRequest request) {
        emailAuthService.verifyCode(request.email(), request.code());
    }

    public record EmailRequest(@Email @NotBlank String email) {}
    public record VerifyRequest(@Email String email, @NotBlank String code) {}
}

