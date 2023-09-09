package com.blueme.backend.dto.usersdto;

import org.hibernate.usertype.UserType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {
	
	private Long id;
	private String accesToken;
	private String email;
	private String nickname;
	private String platformType;
	private String role;
	private String activeStatus;	

}
