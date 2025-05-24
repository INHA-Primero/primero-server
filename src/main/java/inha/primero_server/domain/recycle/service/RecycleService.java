package inha.primero_server.domain.recycle.service;

import inha.primero_server.domain.recycle.dto.response.RecycleHistoryResponse;
import inha.primero_server.domain.recycle.entity.RecycleHistory;
import inha.primero_server.domain.recycle.repository.RecycleHistoryRepository;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import inha.primero_server.global.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecycleService {
    private final RecycleHistoryRepository recycleHistoryRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    private static final int RECYCLE_EXP = 10;

    public Page<RecycleHistoryResponse> getUserHistories(String bearerToken, Pageable pageable) {
        String userUuid = tokenService.validateAndGetUserUuid(bearerToken);
        User user = findUserByUuidOrThrow(userUuid);
        return recycleHistoryRepository.findByUser(user, pageable)
                .map(RecycleHistoryResponse::from);
    }

    public RecycleHistoryResponse getHistoryDetail(String bearerToken, Long historyId) {
        String userUuid = tokenService.validateAndGetUserUuid(bearerToken);
        User user = findUserByUuidOrThrow(userUuid);
        RecycleHistory history = recycleHistoryRepository.findByIdAndUser(historyId, user)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 인증 기록입니다."));
        return RecycleHistoryResponse.from(history);
    }

    @Transactional
    public RecycleHistoryResponse verifyRecycle(String bearerToken, String barcode) {
        // 바코드 소유자 찾기
        User barcodeOwner = userRepository.findByBarcode(barcode)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 바코드입니다."));

        // 인증을 시도하는 사용자 찾기
        String userUuid = tokenService.validateAndGetUserUuid(bearerToken);
        User verifier = findUserByUuidOrThrow(userUuid);

        // 자신의 바코드로는 인증할 수 없음
        if (barcodeOwner.equals(verifier)) {
            RecycleHistory history = RecycleHistory.createFailHistory(barcodeOwner, barcode, "자신의 바코드로는 인증할 수 없습니다.");
            recycleHistoryRepository.save(history);
            return RecycleHistoryResponse.from(history);
        }

        // 경험치 획득 (바코드 소유자에게)
        barcodeOwner.gainExp(RECYCLE_EXP);
        
        // 인증 기록 저장
        RecycleHistory history = RecycleHistory.createSuccessHistory(barcodeOwner, barcode, RECYCLE_EXP);
        recycleHistoryRepository.save(history);
        
        return RecycleHistoryResponse.from(history);
    }

    private User findUserByUuidOrThrow(String uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));
    }
}
