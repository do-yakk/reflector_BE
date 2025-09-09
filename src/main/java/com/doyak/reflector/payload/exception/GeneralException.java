package com.doyak.reflector.payload.exception;

import com.doyak.reflector.payload.code.BaseErrorCode;
import com.doyak.reflector.payload.dto.ErrorReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
	
	private BaseErrorCode code;
	
	public ErrorReasonDTO getErrorReason() {
		return this.code.getErrorReason();
	}
	

}
