package com.blueme.backend.dto.usersdto;

import com.blueme.backend.model.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersUpdateDto {
	
	private String email;
	
	private String password;
	
	private String nickname;
	
	public Users toEntity() {
        return Users.builder().email(email).password(password).nickname(nickname).build();
    }
	
}
