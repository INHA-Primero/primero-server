package inha.primero_server.domain.barcode.controller;

import inha.primero_server.domain.barcode.dto.response.AuthLogRes;
import inha.primero_server.domain.barcode.service.BarcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/barcode")
@RequiredArgsConstructor
public class BarcodeController {
    private final BarcodeService barcodeService;

    @GetMapping("/{code}")
    public ResponseEntity<Long> getUserId(@PathVariable String code) {
        return ResponseEntity.ok(barcodeService.getUserIdByBarcode(code));
    }

    @PostMapping(value = "/log", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> logResult(@RequestParam String code,
                                          @RequestParam boolean success,
                                          @RequestParam String location,
                                          @RequestPart(required = false) MultipartFile image) {
        Long userId = barcodeService.getUserIdByBarcode(code);
        barcodeService.logRecyclingResult(userId, success, location, image);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/log/{userId}")
    public ResponseEntity<List<AuthLogRes>> getLogs(@PathVariable Long userId) {
        return ResponseEntity.ok(barcodeService.getUserLogs(userId));
    }
}
