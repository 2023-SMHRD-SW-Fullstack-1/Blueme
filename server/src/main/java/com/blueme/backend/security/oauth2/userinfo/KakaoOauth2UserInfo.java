package com.blueme.backend.security.oauth2.userinfo;

import java.util.Map;

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

}
