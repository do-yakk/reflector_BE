package com.doyak.reflector.domain.enums;

import lombok.Getter;

@Getter
public enum SortType {
	CREATED_AT("createdAt"),
	UPDATED_AT("updatedAt");
	
	private final String field;
	
	SortType(String field) { this.field = field; }
}