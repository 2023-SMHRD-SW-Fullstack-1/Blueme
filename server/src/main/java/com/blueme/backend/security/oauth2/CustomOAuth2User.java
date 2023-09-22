package com.blueme.backend.security.oauth2;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.blueme.backend.model.entity.UserRole;

import lombok.Getter;


@Getter
public class CustomOAuth2User extends DefaultOAuth2User{
	
	/* Resource Server에서 제공하지 않는 추가정보들을 내 서비스에서 가지고 있기 위해 */
	
	private String email;
	private UserRole role; 

	public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, 
			Map<String, Object> attributes, String nameAttributeKey,
			String email, UserRole role) {
		super(authorities, attributes, nameAttributeKey);
		this.email = email;
		this.role = role;
	}
}
