package com.doyak.reflector.dto.request;

import com.doyak.reflector.domain.enums.Level;
import com.doyak.reflector.domain.enums.Site;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostRequest {

	@Builder
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	public static class PostCommand {
		
	    @Schema(description = "알고리즘 문제 사이트 선택", example = "BAEKJOON")
		private Site site;
	    
	    @Schema(description = "알고리즘 난이도 선택", example = "BAEKJOON_GOLD")
		private Level level;
		
		@NotBlank
		private String title;
		@NotBlank
		private String content;
		
		private String input;
		private String output;
		private String limitTime;
		private String limitMem;
	}
}
