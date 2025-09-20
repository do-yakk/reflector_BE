package com.doyak.reflector.dto.request;

import com.doyak.reflector.domain.enums.Language;

import lombok.Getter;

public interface BlockRequest {

	@Getter
	public class TextCommand implements BlockRequest {
		private String content;
	}
	
	@Getter
	public class CodeCommand implements BlockRequest {
		private String content;
		private Language language;
		private double performTime;
		private double performMem;
	}

}
