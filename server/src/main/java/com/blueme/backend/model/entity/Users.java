package com.blueme.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.blueme.backend.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저 엔터티입니다.
 * <p>
 * 이 엔터티는 사용자의 ID, 이메일, 비밀번호, 닉네임, 플랫폼 타입,
 * 리프레시 토큰, 활성 상태, 권한 등을 속성으로 가지고 있습니다.
 * </p>
 *
 * @author 김혁, 손지연
 * @version 1.0
 * @since 2023-09-06
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Users extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(length = 100, unique = true, nullable = false)
	private String email;

	@Column(length = 255, nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = true, columnDefinition = "VARCHAR(255) default 'blueme'")
	private String platformType;

	private String refreshToken;

	@Column(nullable = true, columnDefinition = "VARCHAR(255) default 'Y'")
	private String activeStatus;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	private String socialId;

	private String img_url;

	@Builder
	public Users(Long id, String email, String password, String nickname, String platformType, UserRole role,
			String refreshToken, String socialId, String img_url) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.refreshToken = refreshToken;
		this.platformType = platformType;
		this.activeStatus = "Y";
		this.role = role;
		this.socialId = socialId;
		this.img_url = img_url;
	}


	/**
	 * authorizeUser 메서드는 해당 유저에게 USER 권한을 부여합니다.
	 */
	public void authorizeUser() {
		this.role = UserRole.USER;
	}

	/**
	 * passwordEncode 메서드는 입력 받은 비밀번호를 암호화합니다.
	 *
	 * @param passwordEncoder BCryptPasswordEncoder 객체 
	 */
	@Builder
	public void passwordEncode(BCryptPasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

	/**
	 * updateRefreshToken 메서드는 리프레시 토큰 값을 갱신합니다.
	 *
	 * @param updateRefreshToken 새로운 리프레시 토큰 값 
	 */
	@Builder
	public void updateRefreshToken(String updateRefreshToken) {
		this.refreshToken = updateRefreshToken;
	}

	/**
	 * update 메서드는 사용자의 닉네임, 비밀번호, 이미지 URL을 수정합니다.
	 *
	 * @param nickname 새로운 닉네임 
	 * @param password 새로운 비밀번호 
	 * @param imgUrl 새로운 이미지 URL  
	 */
	@Builder
	public void update(String nickname, String password, String imgUrl) {
		this.nickname = nickname;
		this.password = password;
		this.img_url = imgUrl;
	}

	/**
	 * 이 overloaded 버전의 update 메서드는 사용자의 닉네임과 이미지 URL만 수정합니다. 
	 * 비밀번호는 변경하지 않습니다.
	 * 
	 * @param nickname2 새로운 닉네임  
	 * @param imgUrl 새로운 이미지 URL  
	 */
	public void update(String nickname2, String imgUrl) {
		this.nickname=nickname2;
		this.img_url=imgUrl;
		
	}
	


}
