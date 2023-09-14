package com.blueme.backend.security.oauth2;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.blueme.backend.model.entity.Users.UserRole;

import lombok.Getter;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User{
	
//	private static final long serialVersionUID = 1L;
	
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
