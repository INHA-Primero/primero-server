package inha.primero_server.domain.user.repository;

import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.entity.Role;
import inha.primero_server.global.common.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByStudentNumber(int studentNumber);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByDeviceUuid(String deviceUuid);
    boolean existsByEmail(String email);
    boolean existsByStudentNumber(int studentNumber);
    boolean existsByNickname(String nickname);
    boolean existsByDeviceUuid(String deviceUuid);
    long countByTotalPointGreaterThan(Integer totalPoint);
    Optional<User> findByUserIdAndStatus(Long userId, Status status);
}