package com.doyak.reflector.payload.code.status;

import org.springframework.http.HttpStatus;

import com.doyak.reflector.payload.code.BaseErrorCode;
import com.doyak.reflector.payload.dto.ErrorReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
	
    // JWT 토큰 관련 에러 
	NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4001", "유효하지 않은 토큰입니다."),
    WRONG_TYPE_SIGNATURE(HttpStatus.UNAUTHORIZED, "TOKEN4002", "잘못된 JWT 서명입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN4003", "토큰이 만료되었습니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4004", "지원되지 않는 JWT 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getErrorReason() {
        return ErrorReasonDTO.builder()
                .httpStatus(httpStatus)
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }
}
