package inha.primero_server.domain.auth.repository;

import inha.primero_server.domain.auth.entity.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {
    Optional<EmailToken> findTopByEmailOrderByExpiredAtDesc(String email);
}