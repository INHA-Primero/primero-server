package inha.primero_server.domain.user.repository;

import inha.primero_server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUuid(String uuid);
    Optional<User> findByBarcode(String barcode);
    Optional<User> findByStudentId(String studentId);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByStudentId(String studentId);
    boolean existsByDeviceUuid(String deviceUuid);
}