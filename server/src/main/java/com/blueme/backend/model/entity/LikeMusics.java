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
 * 사용자가 좋아요한 음악 엔터티 클래스입니다.
 * <p>
 * 이 클래스는 사용자가 좋아하는 음악을 나타냅니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-06
 */
@Getter
@NoArgsConstructor
@Entity
public class LikeMusics extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_music_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@ManyToOne
	@JoinColumn(name = "music_id")
	private Musics music;

	@Builder
	public LikeMusics(Musics music, Users user) {
		this.user = user;
		this.music = music;
	}

}
