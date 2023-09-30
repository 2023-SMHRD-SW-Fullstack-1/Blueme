package com.blueme.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Genres 엔터티는 음악 장르 정보를 표현합니다.
 * <p>
 * 이 엔터티는 각 장르의 고유 ID, 장르 이름, 그리고 장르 이미지 파일 경로를 속성으로 가지고 있습니다.
 * </p>
 *
 * @author 손지연
 * @version 1.0
 * @since 2023-09-06
 */

@Getter
@NoArgsConstructor
@Entity
public class Genres {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "genre_id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(length = 200)
	private String genre_file_path;

	@Builder
	public Genres(String name, String genre_file_path) {
		this.name = name;
		this.genre_file_path = genre_file_path;
	}

}
