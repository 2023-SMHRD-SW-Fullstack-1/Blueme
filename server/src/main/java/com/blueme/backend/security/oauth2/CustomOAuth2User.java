package com.blueme.backend.security.oauth2;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.blueme.backend.enums.UserRole;

import lombok.Getter;

/**
 * 사용자 인증 정보를 담는 클래스
 * DefaultOAuth2User 클래스를 상속받아 구현합니다.
 *  
 * @author 손지연
 * @version 1.0
 * @since 2023-09-22
 */
@Getter
public class CustomOAuth2User extends DefaultOAuth2User{
	
	/* Resource Server에서 제공하지 않는 추가정보들을 내 서비스에서 가지고 있기 위해 */
	
	private String email;
	private UserRole role; 
	
	/**
     * CustomOAuth2User 생성자. 부모 클래스의 생성자를 호출하여 authorities, attributes, nameAttributeKey를 초기화하고,
     * 추가적으로 email과 role을 초기화합니다.
     *
     * @param authorities 사용자 권한 컬렉션
     * @param attributes 속성 값들을 담은 맵
     * @param nameAttributeKey 이름 속성 키 
     * @param email 사용자 이메일
     * @param role 사용자 역할 
     */
	public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, 
			Map<String, Object> attributes, String nameAttributeKey,
			String email, UserRole role) {
		super(authorities, attributes, nameAttributeKey);
		this.email = email;
		this.role = role;
	}
}
