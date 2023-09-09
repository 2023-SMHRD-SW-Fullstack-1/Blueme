package com.blueme.backend.dto.usersdto;

import lombok.Data;

@Data
public class LoginRequestDto {
	private String email;
	private String password;

}
