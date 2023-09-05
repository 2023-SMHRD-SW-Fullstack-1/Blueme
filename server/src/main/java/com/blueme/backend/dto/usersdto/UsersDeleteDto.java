package com.blueme.backend.dto.usersdto;

import com.blueme.backend.model.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersDeleteDto {
	
	private String email;
	
	private String password;
	
	public Users toEntity() {
		return Users.builder().email(email).password(password).build();
	}
}
