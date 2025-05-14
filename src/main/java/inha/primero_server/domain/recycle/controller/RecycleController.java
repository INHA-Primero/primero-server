package inha.primero_server.domain.recycle.controller;

import inha.primero_server.domain.recycle.dto.request.RecycleLogRequest;
import inha.primero_server.domain.recycle.dto.response.RecycleLogDetailResponse;
import inha.primero_server.domain.recycle.dto.response.RecycleLogResponse;
import inha.primero_server.domain.recycle.service.RecycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recycle")
public class RecycleController {

    private final RecycleService recycleService;

    @PostMapping("/failure")
    public ResponseEntity<RecycleLogResponse> createFailureLog(@RequestBody RecycleLogRequest request) {
        return ResponseEntity.ok(recycleService.createFailureLog(request));
    }

    @PostMapping("/success")
    public ResponseEntity<RecycleLogResponse> createSuccessLog(@RequestBody RecycleLogRequest request) {
        return ResponseEntity.ok(recycleService.createSuccessLog(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecycleLogDetailResponse> getRecycleLog(@PathVariable Long id) {
        return ResponseEntity.ok(recycleService.getRecycleLog(id));
    }
}
