package inha.primero_server.domain.join.controller;

import inha.primero_server.domain.join.dto.request.SignUpRequest;
import inha.primero_server.domain.join.service.JoinService;
import inha.primero_server.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/join")
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> signUp(@Valid @RequestBody SignUpRequest request) {
        User user = joinService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "회원가입 성공",
                "status_code", HttpStatus.CREATED.value(),
                "user_id", user.getUserId(),
                "barcode_url", user.getBarcode()
        ));
    }


    @GetMapping("/check/email")
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam String email) {
        return ResponseEntity.ok(joinService.checkEmailDuplicate(email));
    }

    @GetMapping("/check/nickname")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickname) {
        return ResponseEntity.ok(joinService.checkNicknameDuplicate(nickname));
    }

    @GetMapping("/check/student-id")
    public ResponseEntity<Boolean> checkStudentIdDuplicate(@RequestParam String studentId) {
        return ResponseEntity.ok(joinService.checkStudentIdDuplicate(studentId));
    }
}
