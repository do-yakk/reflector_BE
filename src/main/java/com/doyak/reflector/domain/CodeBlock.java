package com.doyak.reflector.domain;

import java.util.HashSet;
import java.util.Set;

import com.doyak.reflector.domain.enums.Language;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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
	
	@ManyToMany
	@JoinTable(
			name = "codeblock_hashtag",
			joinColumns = @JoinColumn(name = "blockId"),
			inverseJoinColumns = @JoinColumn(name = "hashId")
	)
	private Set<Hashtag> hashtags = new HashSet<>();
	
	// 업데이트 
	public void update(String content, Language language, double performTime, double performMem) {
	    this.content = content;
	    this.language = language;
	    this.performTime = performTime;
	    this.performMem = performMem;
	}
	
	// 해시태그 추가/삭제
	public void addHashtag(Hashtag hashtag) {
		this.hashtags.add(hashtag);
		hashtag.getCodeBlocks().add(this);
	}
	
	public void removeHasghtag(Hashtag hashtag) {
		this.hashtags.remove(hashtag);
		hashtag.getCodeBlocks().remove(this);
	}
}
