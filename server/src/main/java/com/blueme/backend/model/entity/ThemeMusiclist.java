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
public class ThemeMusiclist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="theme_musiclist_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="theme_id", insertable=false, updatable=false)
	private Theme theme;
	
	@ManyToOne
	@JoinColumn(name="music_id")
	private Music music;
	
	
	@Builder
	public ThemeMusiclist(Theme theme, Music music) {
		this.theme = theme;
		this.music = music;
	}
}
