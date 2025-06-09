package inha.primero_server.domain.inquiry.repository;

import inha.primero_server.domain.inquiry.entity.Inquiry;
import inha.primero_server.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * InquiryRepository : Inquiry 엔티티에 대한 데이터 액세스 기능을 제공.
 */
@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {
    /**
     * 제목과 내용을 바탕으로 페이징 조건에 따라 문의글을 찾음.
     *
     * @param title 찾으려는 조건의 제목
     * @param content 찾으려는 조건의 내용
     * @param pageable 페이징 조건 (size, sort, page)
     * @return 페이징 결과
     */
    Page<Inquiry> findInquiresByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Inquiry> findAllByUser(User user, Pageable pageable);
}
