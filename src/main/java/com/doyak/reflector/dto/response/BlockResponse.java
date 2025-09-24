package com.doyak.reflector.dto.response;

import java.util.List;

import com.doyak.reflector.domain.enums.Language;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface BlockResponse {
    
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
	public class Text implements BlockResponse {
        private String content;
    }
	
	@Builder
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Getter
    public class Code implements BlockResponse {
        private String content;
        private Language language;
        private double performTime;
        private double performMem;
        private List<String> hashtags;
    }
    
}
