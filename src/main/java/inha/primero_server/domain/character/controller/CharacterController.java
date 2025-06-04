package inha.primero_server.domain.character.controller;

import inha.primero_server.domain.character.entity.Character;
import inha.primero_server.domain.character.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping
    public ResponseEntity<Character> createCharacter(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String nickname) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(characterService.createCharacter(userId, nickname));
    }

    @GetMapping("/me")
    public ResponseEntity<Character> getCurrentCharacter(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(characterService.getCharacter(userId));
    }

    @PutMapping("/me")
    public ResponseEntity<Character> updateCurrentCharacter(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String nickname) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(characterService.updateCharacter(userId, nickname));
    }

    @PostMapping("/me/watering")
    public ResponseEntity<Character> useWateringChance(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(characterService.useWateringChance(userId));
    }
}
