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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

}
