package com.blueme.backend.security.oauth2.userinfo;

import java.util.Map;

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
	
	
	
	

}
