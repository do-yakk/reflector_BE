package com.doyak.reflector.payload.code.status;

import org.springframework.http.HttpStatus;

import com.doyak.reflector.payload.code.BaseErrorCode;
import com.doyak.reflector.payload.dto.ErrorReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

	// 공통 
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
	
    // Post 에러 
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST4001", "게시글을 찾을 수 없습니다."),
    POST_FORBIDDEN(HttpStatus.FORBIDDEN, "POST4002", "다른 사용자의 게시글입니다."),
    PAGE_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "POST4003", "페이지 범위를 벗어났습니다."),
    
    // Block 에러 
    BLOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "BLOCK4001", "블럭을 찾을 수 없습니다."),
    UNSUPPORTED_BLOCK_TYPE(HttpStatus.BAD_REQUEST, "BLOCK4002", "지원하지 않는 블럭타입입니다."),
    BLOCK_FORBIDDEN(HttpStatus.FORBIDDEN, "BLOCK4003", "다른 사용자의 포스트입니다."),

	// JWT 토큰 관련 에러 
	NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4001", "유효하지 않은 토큰입니다."),
    WRONG_TYPE_SIGNATURE(HttpStatus.UNAUTHORIZED, "TOKEN4002", "잘못된 JWT 서명입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN4003", "토큰이 만료되었습니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4004", "지원되지 않는 JWT 토큰입니다."),
    
    // 사용자 관련 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "존재하지 않는 유저입니다."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "이미 존재하는 유저입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER4003", "비밀번호가 불일치 합니다."),
    INVALID_EMAIL(HttpStatus.NOT_FOUND, "USER4004", "인증번호를 확인할 수 없습니다. 이메일을 다시 확인하거나 인증번호를 새로 요청해주세요."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "USER4005", "인증번호가 불일치 합니다.");

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
