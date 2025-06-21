package inha.primero_server.domain.recycle.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RecycleLogRequest {
    private Long binId;
    // private String imgUrl;
    private Long userId;
    private Boolean result;
}