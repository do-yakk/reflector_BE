package com.doyak.reflector.dto.request;

import com.doyak.reflector.domain.enums.Site;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
	private Site site;
	private Integer level;
	private String title;
	private String content;
}
