package inha.primero_server.domain.tree.repository;

import inha.primero_server.domain.tree.entity.Tree;
import inha.primero_server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreeRepository extends JpaRepository<Tree, Long> {
    Tree findByUser(User user);
    List<Tree> findTreesByUser(User user);
}
