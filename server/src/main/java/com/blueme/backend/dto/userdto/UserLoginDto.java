package com.blueme.backend.dto.userdto;

import com.blueme.backend.model.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginDto {
	
	private String email;
	
	private String password;
	
	public User toEntity() {
		return User.builder().email(email).password(password).build();
	}
	
}
