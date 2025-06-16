package inha.primero_server.domain.tree.mapper;

import inha.primero_server.domain.tree.dto.request.TreeCreateRequest;
import inha.primero_server.domain.tree.dto.response.TreeResponse;
import inha.primero_server.domain.tree.entity.Tree;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreeMapper {

    Tree treeCreateRequestToTree(TreeCreateRequest treeCreateRequest);
    TreeResponse treeToTreeResponse(Tree tree);
}
