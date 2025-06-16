package inha.primero_server.domain.tree.service;

import inha.primero_server.domain.tree.dto.request.TreeCreateRequest;
import inha.primero_server.domain.tree.dto.request.TreeUpdateRequest;
import inha.primero_server.domain.tree.dto.response.TreeResponse;
import inha.primero_server.domain.tree.entity.Tree;
import inha.primero_server.domain.tree.mapper.TreeMapper;
import inha.primero_server.domain.tree.repository.TreeRepository;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.domain.user.repository.UserRepository;
import inha.primero_server.domain.util.storage.StorageProvider;
import inha.primero_server.domain.util.storage.dto.StorageUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

import static inha.primero_server.global.common.Constant.TREE_IMAGE_DIR;

@Service
@RequiredArgsConstructor
@Transactional
public class TreeServiceImpl implements TreeService{

    private final TreeRepository treeRepository;
    private final TreeMapper treeMapper;
    private final StorageProvider storageProvider;
    private final UserRepository userRepository;

    @Override
    public List<TreeResponse> getTreeList(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        return treeRepository.findTreesByUser(findUser)
                .stream()
                .map(treeMapper::treeToTreeResponse)
                .toList();
    }

    @Override
    public void createTree(TreeCreateRequest treeCreateRequest, MultipartFile photo){
        User findUser = userRepository.findByEmail(treeCreateRequest.email()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // 1. Tree 엔티티 생성
        Tree tree = treeMapper.treeCreateRequestToTree(treeCreateRequest);
        tree.setUser(findUser);
        Tree saveTree = treeRepository.save(tree);

        // 2. Photo 처리
        String photoUrl = handlePhotoImage(photo, saveTree.getId());
        tree.setPhotoPath(photoUrl);
    }

    @Override
    public void updateTree(TreeUpdateRequest treeUpdateRequest, MultipartFile photo, Long treeId) {
        Tree tree = treeRepository.findById(treeId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 나무입니다."));

        tree.updateTree(treeUpdateRequest.latitude(), treeUpdateRequest.longitude());

        if(!photo.isEmpty()){
            String photoUrl = handlePhotoImage(photo, tree.getId());
            tree.setPhotoPath(photoUrl);
        }
    }

    private String handlePhotoImage(MultipartFile photo, Long treeId) {
        return storageProvider.multipartFileUpload(photo, new StorageUploadRequest(treeId, TREE_IMAGE_DIR));
    }

}
