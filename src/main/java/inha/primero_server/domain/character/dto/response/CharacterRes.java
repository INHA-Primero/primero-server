package inha.primero_server.domain.character.dto.response;

import inha.primero_server.domain.character.entity.Character;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class CharacterRes {

    Long exp;
    Long wateringChance;
    String nickname;

    public CharacterRes(Character character) {
        this.exp = Long.valueOf(character.getExp());
        this.wateringChance = Long.valueOf(character.getWateringChance());
        this.nickname = character.getNickname();
    }
}
