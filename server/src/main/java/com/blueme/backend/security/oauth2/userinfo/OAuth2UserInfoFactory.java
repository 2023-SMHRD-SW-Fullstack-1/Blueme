package com.blueme.backend.security.oauth2.userinfo;

import java.util.Map;

import com.blueme.backend.security.oauth2.SocialType;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(SocialType socialType, Map<String, Object> attributes) {
	     OAuth2UserInfo userInfo;
	        
	        switch (socialType) {
	            case GOOGLE:
	                userInfo = new GoogleOauth2UserInfo(attributes);
	                break;
	            case KAKAO:
	                userInfo = new KakaoOauth2UserInfo(attributes);
	                break;
	            default:
	                throw new IllegalArgumentException("Invalid Provider Type.");
	        }
	        
	        return userInfo;
	    }
	}