package inha.primero_server.domain.recycle.repository;

import inha.primero_server.domain.recycle.entity.Recycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecycleRepository extends JpaRepository<Recycle, Long> {
    Page<Recycle> findAllByOrderByTakenAtDesc(Pageable pageable);
}
