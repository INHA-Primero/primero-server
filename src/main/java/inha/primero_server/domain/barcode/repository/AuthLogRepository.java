package inha.primero_server.domain.barcode.repository;

import inha.primero_server.domain.barcode.entity.AuthLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthLogRepository extends JpaRepository<AuthLog, Long> {
    List<AuthLog> findByUserIdOrderByTimestampDesc(Long userId);
}
