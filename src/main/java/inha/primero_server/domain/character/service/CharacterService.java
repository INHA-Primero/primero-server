package inha.primero_server.domain.character.service;

import inha.primero_server.domain.character.dto.response.CharacterRes;
import inha.primero_server.domain.character.entity.Character;
import inha.primero_server.domain.character.repository.CharacterRepository;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;

    @Transactional
    public Character createCharacter(Long userId, String nickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "User not found"));
        
        Character character = Character.create(user, nickname);
        return characterRepository.save(character);
    }

    public Character getCharacter(Long userId) {
        return characterRepository.findByUserUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "Character not found"));
    }

    @Transactional
    public Character updateCharacter(Long userId, String nickname) {
        Character character = getCharacter(userId);
        character.updateNickname(nickname);
        return character;
    }

    @Transactional
    public void addExp(Long userId, Integer exp) {
        Character character = getCharacter(userId);
        character.addExp(exp);
    }

    @Transactional
    public void addWateringChance(Long userId, Integer chance) {
        Character character = getCharacter(userId);
        character.addWateringChance(chance);
    }

    @Transactional
    public CharacterRes useWateringChance(Long userId) {
        Character character = getCharacter(userId);
        try {
            character.useWateringChance();
            return new CharacterRes(character);
        } catch (IllegalStateException e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER, "No watering chances available");
        }
    }
}
