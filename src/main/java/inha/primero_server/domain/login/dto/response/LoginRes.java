package inha.primero_server.domain.login.dto.response;

public record LoginRes(
        Long userId,
        String treeName,
        Integer characterExp,
        Integer wateringChance,
        String characterNickname
) {
    public static LoginRes of(Long userId, String treeName, Integer characterExp, Integer wateringChance, String characterNickname) {
        return new LoginRes(userId, treeName, characterExp, wateringChance, characterNickname);
    }
}
