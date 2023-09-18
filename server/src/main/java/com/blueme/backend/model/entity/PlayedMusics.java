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
 * 사용자가 재생한 음악 엔터티입니다.
 * <p>
 * 이 클래스는 사용자가 재생한 음악을 관리합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-06
 */
@Getter
@NoArgsConstructor
@Entity
public class PlayedMusics extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "played_music_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@ManyToOne
	@JoinColumn(name = "music_id")
	private Musics music;

	@Builder
	public PlayedMusics(Users user, Musics music) {
		this.user = user;
		this.music = music;
	}
}
