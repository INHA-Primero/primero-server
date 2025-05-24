package inha.primero_server.domain.recycle.repository;

import inha.primero_server.domain.recycle.entity.RecycleHistory;
import inha.primero_server.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecycleHistoryRepository extends JpaRepository<RecycleHistory, Long> {
    Page<RecycleHistory> findByUser(User user, Pageable pageable);
    Optional<RecycleHistory> findByIdAndUser(Long id, User user);
} 