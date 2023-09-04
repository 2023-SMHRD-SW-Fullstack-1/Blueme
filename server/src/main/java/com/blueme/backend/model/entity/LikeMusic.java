package com.blueme.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class LikeMusic extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="like_music_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="music_id")
	private Music music;
	
	@Builder
	public LikeMusic(User user, Music music) {
		this.user = user;
		this.music = music;
	}
	
}
