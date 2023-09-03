package com.blueme.backend.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column(length=100, unique=true, nullable=false)
	private String email;
	
	@Column(length=255, nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String nickname;
	
	@Column(nullable=true, columnDefinition="VARCHAR(255) default 'blueme'")
	private String platformType;
	
	private String accessToken;
	
	@Column(nullable=true, columnDefinition="VARCHAR(255) default 'Y'")
	private String activeStatus;
	
	@OneToMany(mappedBy = "user")
	private List<RecMusiclist> music_list = new ArrayList<RecMusiclist>();
	
	@Builder
	public User(Long id,String email, String password
			,String nickname, String platformType
			, String accessToken, String activeStatus) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.platformType = platformType;
		this.accessToken = accessToken;
		this.activeStatus = activeStatus;
	}

}
