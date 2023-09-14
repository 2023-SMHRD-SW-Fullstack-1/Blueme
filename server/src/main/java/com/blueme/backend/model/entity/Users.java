package com.blueme.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Users extends BaseEntity {

	// 253000 번부터 시작
	/*
	 * @Id
	 * 
	 * @GeneratedValue(generator = "uuid2")
	 * 
	 * @GenericGenerator(name="uuid2", strategy = "uuid2")
	 * 
	 * @Column(name="user_id", columnDefinition = "BINARY(16)")
	 */
	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * 
	 * @Column(name="user_id")
	 */

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.TABLE, generator =
	 * "user_id_generator")
	 * 
	 * @TableGenerator(name = "user_id_generator", table = "id_generator",
	 * pkColumnName = "id_key",
	 * valueColumnName = "id_value", allocationSize = 1, initialValue = 7480000)
	 * 
	 * @Column(name="user_id")
	 */
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

	// @OneToMany(mappedBy = "user")
	// private List<RecMusiclist> music_list = new ArrayList<RecMusiclist>();

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

	public enum UserRole {
		USER,
		ADMIN,
		GUEST
	}

	// /* 유저 권한 설정 (USER) */
	// public void authorizeUser() {
	// this.role = UserRole.USER;
	// }

	/* 비밀번호 암호화 */
	@Builder
	public void passwordEncode(BCryptPasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

	/* 리프레시 토큰(refresh token) 값 갱신 */
	@Builder
	public void updateRefreshToken(String updateRefreshToken) {
		this.refreshToken = updateRefreshToken;
	}

	/* nickname과 password만 수정 가능 */
	@Builder
	public void update(String nickname, String password) {
		this.nickname = nickname;
		this.password = password;
	}

}
