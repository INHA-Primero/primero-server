package inha.primero_server.domain.recycle.service;

import inha.primero_server.domain.bin.entity.Bin;
import inha.primero_server.domain.bin.repository.BinRepository;
import inha.primero_server.domain.recycle.dto.request.RecycleLogRequest;
import inha.primero_server.domain.recycle.dto.response.RecycleLogDetailResponse;
import inha.primero_server.domain.recycle.dto.response.RecycleLogResponse;
import inha.primero_server.domain.recycle.entity.Material;
import inha.primero_server.domain.recycle.entity.RecycleLog;
import inha.primero_server.domain.recycle.repository.RecycleRepository;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecycleService {

    private final RecycleRepository recycleRepository;
    private final UserRepository userRepository;
    private final BinRepository binRepository;

    @Transactional
    public RecycleLogResponse createFailureLog(RecycleLogRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Bin bin = binRepository.findById(request.getBinId())
                .orElseThrow(() -> new RuntimeException("Bin not found"));

        RecycleLog recycleLog = new RecycleLog(
                user,
                bin,
                request.getImgUrl(),
                Material.GENERAL,
                0,
                false
        );

        recycleRepository.save(recycleLog);

        return RecycleLogResponse.builder()
                .success(false)
                .point(0)
                .statusCode(HttpStatus.CREATED.value())
                .message("Create Failure Log Successfully")
                .build();
    }

    @Transactional
    public RecycleLogResponse createSuccessLog(RecycleLogRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Bin bin = binRepository.findById(request.getBinId())
                .orElseThrow(() -> new RuntimeException("Bin not found"));

        RecycleLog recycleLog = new RecycleLog(
                user,
                bin,
                request.getImgUrl(),
                Material.PLASTIC,
                100,
                true
        );

        recycleRepository.save(recycleLog);
        user.addPoints(100);
        userRepository.save(user);

        return RecycleLogResponse.builder()
                .success(true)
                .point(100)
                .statusCode(HttpStatus.CREATED.value())
                .message("Create Success Log Successfully")
                .build();
    }

    public RecycleLogDetailResponse getRecycleLog(Long id) {
        RecycleLog recycleLog = recycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recycle log not found"));

        return RecycleLogDetailResponse.builder()
                .recycleId(recycleLog.getId())
                .binLocation(recycleLog.getBin().getLocation())
                .recordImgPath(recycleLog.getRecordImgPath())
                .takenAt(recycleLog.getTakenAt())
                .result(recycleLog.getResult())
                .statusCode(HttpStatus.OK.value())
                .message("Read log successfully")
                .build();
    }
}
