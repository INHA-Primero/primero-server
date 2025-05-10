package inha.primero_server.domain.inquiry.repository;

import inha.primero_server.domain.inquiry.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {
    Page<Inquiry> findByTitleContainingAndContentContaining(String title, String content, Pageable pageable);
}
