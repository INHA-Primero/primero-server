package inha.primero_server.domain.recycle.repository;

import inha.primero_server.domain.recycle.entity.RecycleLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecycleRepository extends JpaRepository<RecycleLog, Long> {
}
