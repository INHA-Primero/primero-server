package inha.primero_server.domain.tree.repository;

import inha.primero_server.domain.tree.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree, Long> {
}
