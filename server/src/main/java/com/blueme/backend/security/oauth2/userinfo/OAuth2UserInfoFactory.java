package com.blueme.backend.security.oauth2.userinfo;

import java.util.Map;

import com.blueme.backend.security.oauth2.SocialType;

/**
 * OAuth2 사용자 정보를 생성하는 팩토리 클래스.
 * 소셜 플랫폼 타입에 따라 적절한 OAuth2UserInfo 객체를 생성합니다.
 *  
 * @author 손지연
 * @version 1.0
 * @since 2023-09-25
 */
public class OAuth2UserInfoFactory {
	
	/**
     * 주어진 소셜 플랫폼 타입과 속성에 따라 적절한 OAuth2UserInfo 객체를 반환하는 메서드
     *
     * @param socialType 소셜 플랫폼 타입
     * @param attributes 사용자의 속성들을 담은 맵
     * @return 생성된 OAuth2UserInfo 객체
     */
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