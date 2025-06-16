package inha.primero_server.domain.tree.controller;

import inha.primero_server.domain.tree.dto.request.TreeCreateRequest;
import inha.primero_server.domain.tree.dto.request.TreeUpdateRequest;
import inha.primero_server.domain.tree.dto.response.TreeResponse;
import inha.primero_server.domain.tree.service.TreeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "tree controller", description = "나무 API")
@RestController
@RequestMapping("/api/tree")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @GetMapping("/{userId}")
    @Operation(summary = "나무 조회 API", description = "나무 목록을 조회합니다.")
    public ResponseEntity<List<TreeResponse>> getTreeList(@PathVariable Long userId) {
        List<TreeResponse> treeList = treeService.getTreeList(userId);

        return ResponseEntity.ok(treeList);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "나무 등록 API", description = "나무를 등록합니다.")
    public ResponseEntity<Void> createTree(@RequestPart(value = "photo", required = false) MultipartFile photo,
            @Valid @RequestPart(value = "treeCreateRequest") TreeCreateRequest treeCreateRequest) {
        treeService.createTree(treeCreateRequest, photo);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{treeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "나무 수정 API", description = "나무를 수정합니다.")
    public ResponseEntity<Void> updateTree(@RequestPart(required = false) MultipartFile photo,
                                           @Valid @RequestPart(value = "treeUpdateRequest") TreeUpdateRequest treeUpdateRequest,
                                           @PathVariable Long treeId) {
        treeService.updateTree(treeUpdateRequest, photo, treeId);

        return ResponseEntity.ok().build();
    }
}
