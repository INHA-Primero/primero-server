package inha.primero_server.domain.bin.repository;

import inha.primero_server.domain.bin.entity.Bin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BinRepository extends JpaRepository<Bin, Long> {
    Optional<Bin> findByLocation(String location1);
} 