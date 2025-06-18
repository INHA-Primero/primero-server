package inha.primero_server.domain.recycle.repository;

import inha.primero_server.domain.recycle.entity.Recycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecycleRepository extends JpaRepository<Recycle, Long> {
    Page<Recycle> findAllByOrderByTakenAtDesc(Pageable pageable);

    // userId를 받아 해당하는 Recycle만 반환하고 TakenAt 기준으로 내림차순 정렬
    Page<Recycle> findAllByUserUserIdOrderByTakenAtDesc(Long userId, Pageable pageable);
}
