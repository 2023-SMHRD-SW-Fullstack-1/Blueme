package com.blueme.backend.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 추천음악 엔터티입니다.
 * <p>
 * 이 클래스는 사용자는 여러개의 추천음악목록을 가질수 있고
 * 추천음악은 여러개의 추천음악 상세를 가집니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-06
 */
@Getter
@NoArgsConstructor
@Entity
public class RecMusiclists extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rec_musiclist_id")
	private Long id;

	@Column(length = 100)
	private String title;

	@Column(length = 500)
	private String reason;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "rec_musiclist_id") // 셀프조인
	private List<RecMusiclistDetails> recMusicListDetail;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@Builder
	public RecMusiclists(String title, Users user, String reason, List<RecMusiclistDetails> recMusicListDetail) {
		this.title = title;
		this.user = user;
		this.reason = reason;
		this.recMusicListDetail = recMusicListDetail;
	}

}
