package inha.primero_server.domain.inquiry.repository;

import inha.primero_server.domain.inquiry.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
