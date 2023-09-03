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
	private String platform_type;
	
	private String access_token;
	
	@Column(nullable=true, columnDefinition="VARCHAR(255) default 'Y'")
	private String active_status;
	
	@OneToMany(mappedBy = "user")
	private List<RecMusiclist> music_list = new ArrayList<RecMusiclist>();
	
	@Builder
	public User(Long id,String email, String password,String nickname, String platform_type, String access_token, String active_status) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.platform_type = platform_type;
		this.access_token = access_token;
		this.active_status = active_status;
	}

}
