package inha.primero_server.domain.character.controller;

import inha.primero_server.domain.character.dto.response.CharacterRes;
import inha.primero_server.domain.character.entity.Character;
import inha.primero_server.domain.character.service.CharacterService;
import inha.primero_server.global.common.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Character> createCharacter(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String nickname) throws IllegalAccessException {
        // Bearer 토큰을 추출
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalAccessException("incorrect token type");
        }

        String token = authorizationHeader.substring(7); // "Bearer " 부분을 제거

        // JwtUtil을 사용하여 userId를 추출
        Long userId = jwtUtil.getUserId(token);
        return ResponseEntity.ok(characterService.createCharacter(userId, nickname));
    }

    @GetMapping("/me")
    public ResponseEntity<Character> getCurrentCharacter(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws IllegalAccessException {
        // Bearer 토큰을 추출
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalAccessException("incorrect token type");
        }

        String token = authorizationHeader.substring(7); // "Bearer " 부분을 제거

        // JwtUtil을 사용하여 userId를 추출
        Long userId = jwtUtil.getUserId(token);

        return ResponseEntity.ok(characterService.getCharacter(userId));
    }

    @PutMapping("/me")
    public ResponseEntity<Character> updateCurrentCharacter(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String nickname) throws IllegalAccessException {

        // Bearer 토큰을 추출
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalAccessException("incorrect token type");
        }

        String token = authorizationHeader.substring(7); // "Bearer " 부분을 제거

        // JwtUtil을 사용하여 userId를 추출
        Long userId = jwtUtil.getUserId(token);

        return ResponseEntity.ok(characterService.updateCharacter(userId, nickname));
    }

    @PostMapping("/me/watering")
    public ResponseEntity<CharacterRes> useWateringChance(
            @RequestHeader("Authorization") String authorizationHeader
    ) throws IllegalAccessException {
        if (authorizationHeader == null) {
            System.out.println("Authorization header is missing");
        } else {
            System.out.println("Authorization header: " + authorizationHeader);
        }

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalAccessException("incorrect token type");
        }

        String token = authorizationHeader.substring(7); // "Bearer " 부분을 제거

        // JwtUtil을 사용하여 userId를 추출
        Long userId = jwtUtil.getUserId(token);

        return ResponseEntity.ok(characterService.useWateringChance(userId));
    }
}
