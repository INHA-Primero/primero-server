package inha.primero_server.domain.inquiry.repository;

import inha.primero_server.domain.inquiry.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AnswerRepository : Inquiry 엔티티에 대한 데이터 액세스 기능을 제공.
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
