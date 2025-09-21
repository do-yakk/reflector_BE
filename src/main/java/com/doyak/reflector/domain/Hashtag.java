package com.doyak.reflector.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Builder;

@Entity
@Builder
public class Hashtag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hash_id")
	private Long hashId;
	
	private String hash;
	
	@ManyToMany(mappedBy = "hashtags")
	private Set<CodeBlock> codeBlocks = new HashSet<>();
}
