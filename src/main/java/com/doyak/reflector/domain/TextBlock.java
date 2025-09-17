package com.doyak.reflector.domain;

import jakarta.persistence.Entity;
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
public class TextBlock extends Block{
	
	@Lob
	private String content;
	
	// 업데이트  
	public void update(String newContent) {
		this.content = content == null ? "" : content;
	}
}
