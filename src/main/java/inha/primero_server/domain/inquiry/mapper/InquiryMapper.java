package inha.primero_server.domain.inquiry.mapper;

import inha.primero_server.domain.inquiry.dto.response.AnswerResponse;
import inha.primero_server.domain.inquiry.dto.response.InquiryResponse;
import inha.primero_server.domain.inquiry.entity.Answer;
import inha.primero_server.domain.inquiry.entity.Inquiry;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InquiryMapper {

    InquiryResponse toInquiryResponse(Inquiry inquiry);

    List<InquiryResponse> toInquiryResponseList(List<Inquiry> inquiries);

    List<AnswerResponse> toAnswerResponseList(List<Answer> answers);

    AnswerResponse toAnswerResponse(Answer answer);
}
