package com.blueme.backend.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	USER("ROLE_USER"),
	GUEST("ROLE_GUEST"),
	ADMIN("ROLE_ADMIN");
	
	private final String key;

}
