package com.blueme.backend.web.dto.userdto;

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
	private String access_token;
	private String active_status;
	
	public User toEntity() {
        return User.builder().email(email).password(password).nickname(nickname).platform_type(platform_type).access_token(access_token).active_status(active_status).build();
    }
	
}
