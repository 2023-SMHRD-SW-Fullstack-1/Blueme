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
public class PlayedMusics {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="played_music_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private Users user;
	
	@ManyToOne
	@JoinColumn(name="music_id")
	private Musics music;
	
	
	@Builder
	public PlayedMusics(Users user, Musics music) {
		this.user = user;
		this.music = music;
	}
}
