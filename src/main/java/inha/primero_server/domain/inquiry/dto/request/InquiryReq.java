package inha.primero_server.domain.inquiry.dto.request;

import inha.primero_server.domain.inquiry.entity.Inquiry;
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
    @NotBlank @Size(max = 100)
    private String title;
    @NotBlank
    private String content;

    public Inquiry toEntity(){
        return Inquiry.builder()
                .title(title)
                .content(content)
                .build();
    }
}
