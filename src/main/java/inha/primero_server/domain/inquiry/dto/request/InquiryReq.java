package inha.primero_server.domain.inquiry.dto.request;

import inha.primero_server.domain.inquiry.entity.Inquiry;
import inha.primero_server.domain.inquiry.entity.User;
import inha.primero_server.domain.inquiry.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InquiryReq {
    private Integer id;
    @NotBlank @Size(max = 100)
    private String title;
    @NotBlank
    private String content;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private User user;

    public Inquiry toEntity(){
        return Inquiry.builder()
                .id(id)
                .title(title)
                .content(content)
                .status(Status.OPEN)
                .user(user)
                .build();
    }
}
