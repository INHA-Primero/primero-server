package inha.primero_server.domain.tree.service;

import inha.primero_server.domain.tree.dto.request.TreeCreateRequest;
import inha.primero_server.domain.tree.dto.request.TreeUpdateRequest;
import org.springframework.web.multipart.MultipartFile;


public interface TreeService {
    void createTree(TreeCreateRequest treeCreateRequest, MultipartFile photo, String email);
    void updateTree(TreeUpdateRequest treeUpdateReqeuest, MultipartFile photo, Long userId);
}
