package inha.primero_server.domain.recycle.controller;

import com.google.api.Http;
import inha.primero_server.domain.recycle.dto.RecycleDetailResponseDto;
import inha.primero_server.domain.recycle.dto.RecycleListResponseDto;
import inha.primero_server.domain.recycle.dto.request.RecycleLogRequest;
import inha.primero_server.domain.recycle.dto.response.RecycleLogResponse;
import inha.primero_server.domain.recycle.service.RecycleService;
import inha.primero_server.global.common.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/recycles")
@RequiredArgsConstructor
public class RecycleController {

    private final RecycleService recycleService;
    private final JwtUtil jwtUtil;

    @PostMapping("/failure")
    public ResponseEntity<RecycleLogResponse> createFailureLog(
            @RequestBody RecycleLogRequest request) throws IllegalAccessException {
        return ResponseEntity.ok(recycleService.createFailureLog(request));
    }

    @PostMapping("/success")
    public ResponseEntity<RecycleLogResponse> createSuccessLog(
            @RequestBody RecycleLogRequest request) throws IllegalAccessException {
        return ResponseEntity.ok(recycleService.createSuccessLog(request));
    }

    @Operation(
            summary = "Retrieve list of recycles",
            description = "Returns a paginated list of recycle records filtered by user ID, sorted by takenAt"
    )
    @GetMapping
    public ResponseEntity<Page<RecycleListResponseDto>> getAllRecycles(
            @Parameter(
                    name = "pageable",
                    description = "Pagination parameters for the request",
                    example = "{\n  \"page\": 0,\n  \"size\": 10,\n  \"sort\": \"takenAt\"\n}"
            )Pageable pageable,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    ) throws IllegalAccessException {

        String token = authorizationHeader; // "Bearer " 부분을 제거

        // JwtUtil을 사용하여 userId를 추출
        Long userId = jwtUtil.getUserId(token);

        return ResponseEntity.ok(recycleService.getAllRecycles(pageable, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecycleDetailResponseDto> getRecycleById(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    ) throws IllegalAccessException {

        String token = authorizationHeader; // "Bearer " 부분을 제거

        // JwtUtil을 사용하여 userId를 추출
        Long userId = jwtUtil.getUserId(token);

        return ResponseEntity.ok(recycleService.getRecycleById(id, userId));
    }

//    @PostMapping("/{id}")
//    public ResponseEntity<Void> createRecycleLog(
//            @PathVariable Long id,
//            @RequestPart(value = "photo", required = false) MultipartFile photo
//    ) {
//        recycleService.createRecycleLog(id, photo);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping(value = "/v2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecycleLogResponse> createRecycleLogV2(
            @RequestPart(value = "request") RecycleLogRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ){
        return ResponseEntity.ok(recycleService.createRecycleLogV2(request, photo));
    }

    @PostMapping(value = "/create-dummy-log", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createDummyLog(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IllegalAccessException {

        String token = authorizationHeader; // "Bearer " 부분을 제거

        // JwtUtil을 사용하여 userId를 추출
        Long userId = jwtUtil.getUserId(token);

        recycleService.createRecycleLogV2(
                new RecycleLogRequest(1L, userId, false), photo);
        recycleService.createRecycleLogV2(
                new RecycleLogRequest(2L, userId, false), photo);
        recycleService.createRecycleLogV2(
                new RecycleLogRequest(1L, userId, true), photo);
        recycleService.createRecycleLogV2(
                new RecycleLogRequest(2L, userId, true), photo);
        recycleService.createRecycleLogV2(
                new RecycleLogRequest(2L, userId, true), photo);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
