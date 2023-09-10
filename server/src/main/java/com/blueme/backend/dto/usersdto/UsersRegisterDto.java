package com.blueme.backend.dto.usersdto;

import com.blueme.backend.model.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersRegisterDto {
	
	private String email;
	
	private String password;
	
	private String nickname;
	
	private String platform_type;
	
	private String refreshToken;
	
	private String activeStatus;
	
	public Users toEntity() {
        return Users.builder().email(email).password(password).nickname(nickname).platformType(platform_type).refreshToken(refreshToken).build();
    }
	
}
