package com.blueme.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자의 권한과 관련된 enum 클래스입니다.
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-22
 */

@Getter
@RequiredArgsConstructor
public enum UserRole {
	USER("ROLE_USER"),
	GUEST("ROLE_GUEST"),
	ADMIN("ROLE_ADMIN");
	
	private final String key;

}
