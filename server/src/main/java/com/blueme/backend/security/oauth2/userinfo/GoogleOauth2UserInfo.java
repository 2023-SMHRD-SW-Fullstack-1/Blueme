package com.blueme.backend.security.oauth2.userinfo;

import java.util.Map;

/**
 * Google OAuth2 사용자 정보를 처리하는 클래스
 * OAuth2UserInfo 추상 클래스를 상속받아 구현합니다.
 *  
 * @author 손지연
 * @version 1.0
 * @since 2023-09-22
 */

public class GoogleOauth2UserInfo extends OAuth2UserInfo {

	public GoogleOauth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return (String) attributes.get("sub");
	}

	@Override
	public String getNickname() {
		return (String) attributes.get("name");
	}

	@Override
	public String getImageUrl() {
		return (String) attributes.get("picture");
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}
	
	
	
	

}
