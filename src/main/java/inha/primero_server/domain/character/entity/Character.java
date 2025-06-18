package inha.primero_server.domain.character.entity;

import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "character_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer exp = 0;

    @Column(nullable = false)
    private Integer wateringChance = 0;

    @Column(nullable = false)
    private String nickname;

    public static Character create(User user, String nickname) {
        Character character = new Character();
        character.user = user;
        character.nickname = nickname;
        return character;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addExp(Integer exp) {
        this.exp += exp;
    }

    public void addWateringChance(Integer chance) {
        this.wateringChance += chance;
    }

    public void useWateringChance() {
        if (this.wateringChance <= 0) {
            throw new IllegalStateException("No watering chances available");
        }
        this.wateringChance--;
        this.exp += 100;
    }
} 