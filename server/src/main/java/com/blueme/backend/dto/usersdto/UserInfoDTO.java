package com.blueme.backend.dto.usersdto;

import com.blueme.backend.model.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDTO {
	
	private Long id;
	private String email;
	private String nickname;
	private String img_url;
	

	public Users toEntity() {
		return Users.builder().id(id).email(email).nickname(nickname).img_url(img_url).build();
	}

}
