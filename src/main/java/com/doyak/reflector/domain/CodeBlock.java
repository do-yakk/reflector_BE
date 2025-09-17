package com.doyak.reflector.domain;

import com.doyak.reflector.domain.enums.Language;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class CodeBlock extends Block {
	
	@Lob
	private String content;
	
	@Enumerated(EnumType.STRING)
	private Language language;
	
	private double performTime;
	private double performMem;
	
	// 업데이트 
	public void update(String content, Language language, double performTime, double performMem) {
	    this.content = content;
	    this.language = language;
	    this.performTime = performTime;
	    this.performMem = performMem;
	}
}
