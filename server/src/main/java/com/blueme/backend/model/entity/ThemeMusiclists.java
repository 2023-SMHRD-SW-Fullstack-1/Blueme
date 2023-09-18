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

/**
 * 테마음악 엔터티입니다.
 * <p>
 * 테마 음악의 상세 리스트를 저장합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-06
 */
@Getter
@NoArgsConstructor
@Entity
public class ThemeMusiclists {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "theme_musiclist_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "theme_id", insertable = false, updatable = false)
	private Themes theme;

	@ManyToOne
	@JoinColumn(name = "music_id")
	private Musics music;

	@Builder
	public ThemeMusiclists(Themes theme, Musics music) {
		this.theme = theme;
		this.music = music;
	}
}
