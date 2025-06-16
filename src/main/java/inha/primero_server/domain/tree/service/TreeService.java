package inha.primero_server.domain.tree.service;

import inha.primero_server.domain.tree.dto.request.TreeCreateRequest;
import inha.primero_server.domain.tree.dto.request.TreeUpdateRequest;
import inha.primero_server.domain.tree.dto.response.TreeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface TreeService {
    List<TreeResponse> getTreeList(Long userId);
    void createTree(TreeCreateRequest treeCreateRequest, MultipartFile photo);
    void updateTree(TreeUpdateRequest treeUpdateRequest, MultipartFile photo, Long treeId);
}
