package com.blueme.backend.security.oauth2.dto;

import java.io.Serializable;

import com.blueme.backend.model.entity.Users;

public class SessionUserDto implements Serializable {

	private final String name;
	private final String email;
	private final String profile;

	public SessionUserDto(Users user) {
		this.name = user.getNickname();
		this.email = user.getEmail();
		this.profile = user.getImg_url();
	}
}
