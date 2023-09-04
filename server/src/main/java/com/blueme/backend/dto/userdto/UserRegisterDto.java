package com.blueme.backend.dto.userdto;

import com.blueme.backend.model.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisterDto {
	
	private String email;
	
	private String password;
	
	private String nickname;
	
	private String platform_type;
	
	private String accessToken;
	
	private String activeStatus;
	
	public User toEntity() {
        return User.builder().email(email).password(password).nickname(nickname).platformType(platform_type).accessToken(accessToken).build();
    }
	
}
