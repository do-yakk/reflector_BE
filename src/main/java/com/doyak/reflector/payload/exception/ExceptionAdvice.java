package com.doyak.reflector.payload.exception;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.doyak.reflector.payload.ApiResponse;
import com.doyak.reflector.payload.code.status.ErrorStatus;
import com.doyak.reflector.payload.dto.ErrorReasonDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@RestControllerAdvice
public class ExceptionAdvice {
    
    // 정의내린(예측한) GeneralException 처리 
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleGeneralException(GeneralException e, WebRequest request) {
        ErrorReasonDTO errorReason = e.getErrorReason();
        return handleExceptionInternal(e, errorReason, new HttpHeaders(), request);
    }

    // 예측 못한 예외 처리 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, WebRequest request) {
        // 추적을 위한 TraceId
    	String traceId = UUID.randomUUID().toString();
        log.error("[TraceId: {}] Unexpected exception", traceId, e);
        return handleInternalError(
                e,
                ErrorStatus.INTERNAL_SERVER_ERROR,
                ErrorStatus.INTERNAL_SERVER_ERROR.getHttpStatus(),
                request,
                traceId
        );
    }
    
    // GeneralException 처리에 쓰이는 내부 로직  
    private ResponseEntity<Object> handleExceptionInternal(
            Exception e,
            ErrorReasonDTO reason,
            HttpHeaders headers,
            WebRequest request
    ) {
        ApiResponse<Object> body = ApiResponse.onFailure(
                reason.getCode(),
                reason.getMessage(),
                null
        );
        return createResponseEntity(body, headers, reason.getHttpStatus());
    }


    // 예측하지 못한 내부 서버 에러 처리 로직 
    private ResponseEntity<Object> handleInternalError(
            Exception e,
            ErrorStatus errorStatus,
            HttpStatus status,
            WebRequest request,
            String errorPoint
    ) {
        ApiResponse<Object> body = ApiResponse.onFailure(
                errorStatus.getCode(),
                errorStatus.getMessage(),
                errorPoint
        );
        return createResponseEntity(body, HttpHeaders.EMPTY, status);
    }

    // ResponseEntity 생성 
    private ResponseEntity<Object> createResponseEntity(
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatus status
    ) {
        return new ResponseEntity<>(body, headers, status);
    }

}