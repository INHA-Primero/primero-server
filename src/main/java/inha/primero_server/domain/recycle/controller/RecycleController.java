package inha.primero_server.domain.recycle.controller;

import inha.primero_server.domain.recycle.dto.response.RecycleHistoryResponse;
import inha.primero_server.domain.recycle.service.RecycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recycle")
@RequiredArgsConstructor
public class RecycleController {
    private final RecycleService recycleService;

    @GetMapping("/histories")
    public Page<RecycleHistoryResponse> getUserHistories(
            @RequestHeader("Authorization") String bearerToken,
            Pageable pageable) {
        return recycleService.getUserHistories(bearerToken, pageable);
    }

    @GetMapping("/histories/{historyId}")
    public RecycleHistoryResponse getHistoryDetail(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long historyId) {
        return recycleService.getHistoryDetail(bearerToken, historyId);
    }

    @PostMapping("/verify")
    public RecycleHistoryResponse verifyRecycle(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam String barcode) {
        return recycleService.verifyRecycle(bearerToken, barcode);
    }
}
