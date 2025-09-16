package com.doyak.reflector.dto.request;

import com.doyak.reflector.domain.enums.Site;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
		
		@NotNull
		private Site site;
		private Integer level;
		
		@NotBlank
		private String title;
		@NotBlank
		private String content;
	}
}
