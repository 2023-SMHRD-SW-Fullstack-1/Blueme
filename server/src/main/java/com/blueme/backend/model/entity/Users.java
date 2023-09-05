package com.blueme.backend.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.GenericGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Users extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
    @SequenceGenerator(name = "user_seq_generator", sequenceName = "user_sequence", allocationSize = 1)
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
	
	@Enumerated(EnumType.STRING)
    private UserRole role;
	
//	@OneToMany(mappedBy = "user")
//	private List<RecMusiclist> music_list = new ArrayList<RecMusiclist>();
	
	@Builder
	public Users(Long id,String email, String password
			,String nickname, String platformType
			, String accessToken) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.accessToken = accessToken;
		this.platformType = platformType;
		this.activeStatus = "Y";
		this.role = UserRole.ADMIN;
	}
	
	public enum UserRole {
	    USER,
	    ADMIN
	}
	
}

