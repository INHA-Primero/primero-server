package inha.primero_server.domain.barcode.service;

import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import inha.primero_server.global.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BarcodeService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public String generateBarcode(String studentId) {
        return "PRIM" + studentId;
    }

    public boolean validateBarcode(String barcode) {
        if (barcode == null || !barcode.startsWith("PRIM")) {
            return false;
        }

        String studentId = barcode.substring(4);
        return userRepository.existsByStudentId(studentId);
    }

    public User getUserByBarcode(String barcode) {
        if (!validateBarcode(barcode)) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "유효하지 않은 바코드입니다.");
        }

        String studentId = barcode.substring(4);
        return userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));
    }

    private User findUserByUuidOrThrow(String uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));
    }
}
