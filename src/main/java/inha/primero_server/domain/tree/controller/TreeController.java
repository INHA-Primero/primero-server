package inha.primero_server.domain.tree.controller;

import inha.primero_server.domain.tree.dto.request.TreeCreateRequest;
import inha.primero_server.domain.tree.dto.request.TreeUpdateRequest;
import inha.primero_server.domain.tree.service.TreeService;
import inha.primero_server.global.common.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "tree controller", description = "나무 API")
@RestController
@RequestMapping("/api/tree")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;
    private final JwtUtil jwtUtil;

    @PostMapping("")
    @Operation(summary = "나무 등록 API", description = "나무를 등록합니다.")
    public ResponseEntity<Void> crateTree(@RequestParam(value = "photo", required = false) MultipartFile photo,
            TreeCreateRequest treeCreateRequest, String email) {
        treeService.createTree(treeCreateRequest, photo, email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "나무 수정 API", description = "나무를 수정합니다.")
    public ResponseEntity<Void> updateTree(@RequestParam(value = "photo", required = false) MultipartFile photo,
                                           TreeUpdateRequest treeUpdateRequest, @PathVariable Long userId) {
        treeService.updateTree(treeUpdateRequest, photo, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
