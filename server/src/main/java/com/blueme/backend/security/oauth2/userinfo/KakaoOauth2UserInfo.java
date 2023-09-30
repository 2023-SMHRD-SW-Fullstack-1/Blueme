package com.blueme.backend.security.oauth2.userinfo;

import java.util.Map;

/**
 * Kakao OAuth2 사용자 정보를 처리하는 클래스
 * OAuth2UserInfo 추상 클래스를 상속받아 구현합니다.
 *  
 * @author 손지연
 * @version 1.0
 * @since 2023-09-27
 */

public class KakaoOauth2UserInfo extends OAuth2UserInfo {
	
	public KakaoOauth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public String getNickname() {
		Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) account.get("profile");
		
		if(account == null || profile == null) {
			return null;
		}
		
		return (String) profile.get("nickname");
	}

	@Override
	public String getImageUrl() {
		Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) account.get("profile");

		if(account == null || profile == null) {
			return null;
		}
		
		return (String) profile.get("thumbnail_image_url");
	}

	@Override
	public String getEmail() {
		Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) account.get("profile");
		System.out.println("email :::: "+ account.get("email"));

		if(account == null || profile == null) {
			return null;
		}
		return (String) account.get("email");
	}

}
