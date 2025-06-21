package inha.primero_server.domain.recycle.controller;

import inha.primero_server.domain.recycle.dto.RecycleDetailResponseDto;
import inha.primero_server.domain.recycle.dto.RecycleListResponseDto;
import inha.primero_server.domain.recycle.dto.request.RecycleLogRequest;
import inha.primero_server.domain.recycle.dto.response.RecycleLogResponse;
import inha.primero_server.domain.recycle.service.RecycleService;
import inha.primero_server.global.common.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<Page<RecycleListResponseDto>> getAllRecycles(
            Pageable pageable,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws IllegalAccessException {
        // Bearer 토큰을 추출
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalAccessException("incorrect token type");
        }

        String token = authorizationHeader.substring(7); // "Bearer " 부분을 제거

        // JwtUtil을 사용하여 userId를 추출
        Long userId = jwtUtil.getUserId(token);

        return ResponseEntity.ok(recycleService.getAllRecycles(pageable, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecycleDetailResponseDto> getRecycleById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) throws IllegalAccessException {

        // Bearer 토큰을 추출
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalAccessException("incorrect token type");
        }

        String token = authorizationHeader.substring(7); // "Bearer " 부분을 제거

        // JwtUtil을 사용하여 userId를 추출
        Long userId = jwtUtil.getUserId(token);

        return ResponseEntity.ok(recycleService.getRecycleById(id, userId));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> createRecycleLog(@PathVariable Long id,
                                                 @RequestPart(value = "photo", required = false) MultipartFile photo) {
        recycleService.createRecycleLog(id, photo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
