package inha.primero_server.domain.recycle.service;

import inha.primero_server.domain.bin.entity.Bin;
import inha.primero_server.domain.bin.repository.BinRepository;
import inha.primero_server.domain.character.service.CharacterService;
import inha.primero_server.domain.recycle.dto.RecycleDetailResponseDto;
import inha.primero_server.domain.recycle.dto.RecycleListResponseDto;
import inha.primero_server.domain.recycle.dto.request.RecycleLogRequest;
import inha.primero_server.domain.recycle.dto.response.RecycleLogDetailResponse;
import inha.primero_server.domain.recycle.dto.response.RecycleLogResponse;
import inha.primero_server.domain.recycle.entity.Material;
import inha.primero_server.domain.recycle.entity.Recycle;
import inha.primero_server.domain.recycle.repository.RecycleRepository;
import inha.primero_server.domain.tree.entity.Tree;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.domain.util.storage.StorageProvider;
import inha.primero_server.domain.util.storage.dto.StorageUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static inha.primero_server.global.common.Constant.RECYCLE_IMAGE_DIR;
import static inha.primero_server.global.common.Constant.TREE_IMAGE_DIR;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecycleService {

    private final RecycleRepository recycleRepository;
    private final UserRepository userRepository;
    private final BinRepository binRepository;
    private final CharacterService characterService;
    private final StorageProvider storageProvider;

    @Transactional
    public RecycleLogResponse createFailureLog(RecycleLogRequest request) {
        /*
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
        */

        // createRecycleLogV2(request, false, photo);

        return RecycleLogResponse.builder()
                .success(false)
                .point(0)
                .statusCode(HttpStatus.CREATED.value())
                .message("Create Failure Log Successfully")
                .build();
    }

    @Transactional
    public RecycleLogResponse createSuccessLog(RecycleLogRequest request) {
        /*
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
        
        // Update user points
        user.addTotalPoint(100);
        userRepository.save(user);
        
        // Update character watering chance
        characterService.addWateringChance(user.getUserId(), 1);
        */

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

    public Page<RecycleListResponseDto> getAllRecycles(Pageable pageable, Long userId) {
        return recycleRepository.findAllByUserUserIdOrderByTakenAtDesc(userId, pageable)
                .map(RecycleListResponseDto::new);
    }

    public RecycleDetailResponseDto getRecycleById(Long id, Long userId) {
        Recycle recycle = recycleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recycle not found with id: " + id));
        return new RecycleDetailResponseDto(recycle);
    }

    @Transactional
    public void createRecycleLog(Long id, MultipartFile photo) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Bin bin = binRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Bin not found"));

        // 1. Recycle 엔티티 생성
        Recycle recycle = new Recycle(
                findUser,
                bin,
                "",
                Material.PLASTIC,
                100,
                true
        );
        Recycle saveRecycle = recycleRepository.save(recycle);

        // 2. Photo 처리
        String photoUrl = handlePhotoImage(photo, saveRecycle.getId(), findUser.getUserId());
        saveRecycle.setRecordImgPath(photoUrl);
    }

    @Transactional
    public RecycleLogResponse createRecycleLogV2(
            RecycleLogRequest request, MultipartFile photo
    ) {
        User findUser = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Bin bin = binRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Bin not found"));

        Recycle recycle = null;

        // 1. Recycle 엔티티 생성
        // success
        if(request.getResult()){
            recycle = new Recycle(
                    findUser,
                    bin,
                    "",
                    Material.PLASTIC,
                    100,
                    request.getResult()
            );

            // Update user points
            findUser.addTotalPoint(100);
            userRepository.save(findUser);

            // Update character watering chance
            characterService.addWateringChance(findUser.getUserId(), 1);

        // failure
        }else{
            recycle = new Recycle(
                    findUser,
                    bin,
                    "",
                    Material.GENERAL,
                    0,
                    request.getResult()
            );
        }

        Recycle saveRecycle = recycleRepository.save(recycle);

        // 2. Photo 처리
        String photoUrl = handlePhotoImage(photo, saveRecycle.getId(), findUser.getUserId());
        saveRecycle.setRecordImgPath(photoUrl);

        if(request.getResult()){
            return RecycleLogResponse.builder()
                    .success(request.getResult())
                    .point(100)
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Create Success Log Successfully")
                    .build();
        }else{
            return RecycleLogResponse.builder()
                    .success(request.getResult())
                    .point(0)
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Create Failure Log Successfully")
                    .build();
        }
    }

    private String handlePhotoImage(MultipartFile photo, Long recycleId, Long userId) {
        String path = RECYCLE_IMAGE_DIR + "/" + userId;
        return storageProvider.multipartFileUpload(photo, new StorageUploadRequest(recycleId, path));
    }
}
