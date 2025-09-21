package com.doyak.reflector.dto.response;

import java.util.List;

import com.doyak.reflector.domain.enums.Site;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostResponse {
	
	@Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
	public static class PostInfo {
		private Long postId;
		private Site site;
		private Integer level;
		private String title;
		private String content;	
		private String author;
		private String createdAt;
	    private String updatedAt;
	    private List<BlockResponse> blocks;
	}
	
	@Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
	public static class PostOverview {
		private Long postId;
		private Site site;
		private Integer level;
		private String title;
	}
}
