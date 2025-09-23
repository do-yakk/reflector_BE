package com.doyak.reflector.dto.request;

import java.util.List;

import com.doyak.reflector.domain.enums.Language;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BlockRequest {
	
	public interface BlockCommand {} 

	@Builder
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class TextCommand implements BlockCommand {
		private String content;
	}
	
	@Builder
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class CodeCommand implements BlockCommand {
		private String content;
		private Language language;
		private double performTime;
		private double performMem;
		private List<String> hashtags;
	}
	
	@Builder
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class ReorderBlock {
		private Integer newIndex;
	}

}
