package inha.primero_server.global.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, 404),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401),
    CONFLICT(HttpStatus.CONFLICT, 409),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, 400),

    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, 401),
    DUPLICATE_OBJECT(HttpStatus.CONFLICT, 409),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, 401),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, 401);

    private final HttpStatus httpStatus;
    private final int code;
}