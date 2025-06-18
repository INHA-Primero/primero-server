package inha.primero_server.domain.login.service;

import inha.primero_server.domain.character.entity.Character;
import inha.primero_server.domain.character.repository.CharacterRepository;
import inha.primero_server.domain.login.dto.request.LoginReq;
import inha.primero_server.domain.login.dto.response.LoginRes;
import inha.primero_server.domain.login.repository.LoginRepository;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.JwtUtil;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final CharacterRepository characterRepository;
    private final JwtUtil jwtUtil;

    public LoginRes login(LoginReq request) {
        User user = loginRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        Character character = characterRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "캐릭터 정보를 찾을 수 없습니다."));

        String token = jwtUtil.createAccessToken(user.getUserId(), user.getEmail(), user.getRole().name());

        return LoginRes.of(
                user.getUserId(),
                user.getTreeName(),
                character.getExp(),
                character.getWateringChance(),
                character.getNickname(),
                token
        );
    }
}
