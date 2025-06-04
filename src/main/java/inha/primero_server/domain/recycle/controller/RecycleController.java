package inha.primero_server.domain.recycle.controller;

import inha.primero_server.domain.recycle.dto.RecycleDetailResponseDto;
import inha.primero_server.domain.recycle.dto.RecycleListResponseDto;
import inha.primero_server.domain.recycle.dto.request.RecycleLogRequest;
import inha.primero_server.domain.recycle.dto.response.RecycleLogResponse;
import inha.primero_server.domain.recycle.service.RecycleService;
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

    @PostMapping("/failure")
    public ResponseEntity<RecycleLogResponse> createFailureLog(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RecycleLogRequest request) {
        Long userId = Long.parseLong(userDetails.getUsername());
        request.setUserId(userId);
        return ResponseEntity.ok(recycleService.createFailureLog(request));
    }

    @PostMapping("/success")
    public ResponseEntity<RecycleLogResponse> createSuccessLog(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RecycleLogRequest request) {
        Long userId = Long.parseLong(userDetails.getUsername());
        request.setUserId(userId);
        return ResponseEntity.ok(recycleService.createSuccessLog(request));
    }

    @GetMapping
    public ResponseEntity<Page<RecycleListResponseDto>> getAllRecycles(Pageable pageable) {
        return ResponseEntity.ok(recycleService.getAllRecycles(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecycleDetailResponseDto> getRecycleById(@PathVariable Long id) {
        return ResponseEntity.ok(recycleService.getRecycleById(id));
    }

}
