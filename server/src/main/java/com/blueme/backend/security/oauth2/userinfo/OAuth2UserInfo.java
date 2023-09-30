package com.blueme.backend.security.oauth2.userinfo;

import java.util.Map;

/**
 * OAuth2 사용자 정보를 처리하는 추상 클래스.
 * 각 소셜 플랫폼 별로 이 클래스를 상속받아 필요한 메소드를 구현합니다
 *  
 * @author 손지연
 * @version 1.0
 * @since 2023-09-22
 */

public abstract class OAuth2UserInfo {
	
	protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"

    public abstract String getNickname();
    
    public abstract String getEmail();

    public abstract String getImageUrl();

}
