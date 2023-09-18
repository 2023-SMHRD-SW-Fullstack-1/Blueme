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
 * 추천음악상세 엔터티입니다.
 * <p>
 * 이 클래스는 추천음악상세에 관한 추천음악, 음악에 관계 됩니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-06
 */
@Getter
@NoArgsConstructor
@Entity
public class RecMusiclistDetails extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rec_musiclist_detail_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "rec_musiclist_id", insertable = false, updatable = false)
	private RecMusiclists recMusiclist;

	@ManyToOne
	@JoinColumn(name = "music_id")
	private Musics music;

	@Builder
	public RecMusiclistDetails(RecMusiclists recMusiclist, Musics music) {
		this.recMusiclist = recMusiclist;
		this.music = music;
	}

}
