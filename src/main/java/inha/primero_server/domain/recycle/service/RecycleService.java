package inha.primero_server.domain.recycle.service;

import inha.primero_server.domain.bin.entity.Bin;
import inha.primero_server.domain.bin.repository.BinRepository;
import inha.primero_server.domain.recycle.dto.RecycleDetailResponseDto;
import inha.primero_server.domain.recycle.dto.RecycleListResponseDto;
import inha.primero_server.domain.recycle.dto.request.RecycleLogRequest;
import inha.primero_server.domain.recycle.dto.response.RecycleLogDetailResponse;
import inha.primero_server.domain.recycle.dto.response.RecycleLogResponse;
import inha.primero_server.domain.recycle.entity.Material;
import inha.primero_server.domain.recycle.entity.Recycle;
import inha.primero_server.domain.recycle.repository.RecycleRepository;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        Recycle recycle = new Recycle(
                user,
                bin,
                request.getImgUrl(),
                Material.GENERAL,
                0,
                false
        );

        recycleRepository.save(recycle);

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

        Recycle recycle = new Recycle(
                user,
                bin,
                request.getImgUrl(),
                Material.PLASTIC,
                100,
                true
        );

        recycleRepository.save(recycle);
        //user.setTotalPoint(user.getTotalPoint() + 100);
        userRepository.save(user);

        return RecycleLogResponse.builder()
                .success(true)
                .point(100)
                .statusCode(HttpStatus.CREATED.value())
                .message("Create Success Log Successfully")
                .build();
    }

    public RecycleLogDetailResponse getRecycleLog(Long id) {
        Recycle recycle = recycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recycle log not found"));

        return RecycleLogDetailResponse.builder()
                .recycleId(recycle.getId())
                .binLocation(recycle.getBin().getLocation())
                .recordImgPath(recycle.getRecordImgPath())
                .takenAt(recycle.getTakenAt())
                .result(recycle.getResult())
                .statusCode(HttpStatus.OK.value())
                .message("Read log successfully")
                .build();
    }

    public Page<RecycleListResponseDto> getAllRecycles(Pageable pageable) {
        return recycleRepository.findAllByOrderByTakenAtDesc(pageable)
                .map(RecycleListResponseDto::new);
    }

    public RecycleDetailResponseDto getRecycleById(Long id) {
        Recycle recycle = recycleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recycle not found with id: " + id));
        return new RecycleDetailResponseDto(recycle);
    }

    @Transactional
    public void createSuccessLog(User user, Recycle recycle) {
        recycleRepository.save(recycle);
        //user.setTotalPoint(user.getTotalPoint() + 100);
        userRepository.save(user);
    }
}
