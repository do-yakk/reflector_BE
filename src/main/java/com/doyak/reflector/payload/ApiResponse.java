package com.doyak.reflector.payload;

import com.doyak.reflector.payload.code.status.SuccessStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	
	private String code;
	private String message;
	private T data;

	
	public static <T> ApiResponse<T> onSuccess(T data) {
        return ApiResponse.<T>builder()
                .code(SuccessStatus.OK.getCode()) 
                .message(SuccessStatus.OK.getMessage())
                .data(data)
                .build();
    }
	
	public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

}
