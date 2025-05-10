package inha.primero_server.domain.inquiry.dto.response;

import inha.primero_server.domain.inquiry.entity.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class InquiryPagingRes {
    private List<InquiryRes> inquiryRes;
    private int previous;
    private int next;
    private boolean hasNext;
    private boolean hasPrevious;
    private int totalPage;

    public static InquiryPagingRes from(Page<Inquiry> page) {
        List<InquiryRes> inquiryResList = page.getContent().stream()
                .map(InquiryRes::new)
                .toList();

        int currentPage   = page.getNumber();               // 0부터 시작
        int totalPages    = page.getTotalPages();           // 전체 페이지 수
        boolean hasPrev   = page.hasPrevious();
        boolean hasNext   = page.hasNext();

        // 1) 이전 페이지
        int prevPage = page.previousOrFirstPageable().getPageNumber();
        // 2) 다음 페이지
        int nextPage = page.nextOrLastPageable().getPageNumber();  // 마지막이면 그대로

        return new InquiryPagingRes(
                inquiryResList,
                prevPage,
                nextPage,
                hasNext,
                hasPrev,
                totalPages
        );
    }
}
