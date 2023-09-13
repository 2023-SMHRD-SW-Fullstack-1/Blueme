package com.blueme.backend.model.entity;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.blueme.backend.config.FilePathConfig;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
작성자: 김혁
날짜(수정포함): 2023-09-13
설명: 음악 엔터티
*/

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Musics {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "music_id")
	private Long id;
	private String title;
	private String artist;
	private String album;
	private String filePath;
	private String jacketFilePath;
	private String artistFilePath;
	private String genre1;
	private String genre2;
	@Lob
	private String tag;
	private String time;
	@Column(name = "hit", columnDefinition = "bigint default 0")
	private Long hit;
	@Lob
	private String lyrics;
	@Column(name = "release_date", columnDefinition = "VARCHAR(50)")
	private String releaseDate;

	// 파일경로 base64로 변환되게 사용(코드중복최소화, 유지보수)(DB에 영속X)
	@Transient
	public String getJacketFile() {
		try {
			Path filePath = Paths.get(FilePathConfig.JACKET_PATH + jacketFilePath + ".jpg");
			File file = filePath.toFile();
			ImageConverter<File, String> converter = new ImageToBase64();
			String base64 = null;
			base64 = converter.convert(file);
			return base64;
		} catch (Exception e) {
			throw new RuntimeException("재킷파일 전송 실패", e);
		}
	}

	@Transient
	public String getArtistFile() {
		try {
			Path filePath = Paths.get(FilePathConfig.ARTIST_PATH + artistFilePath + ".jpg");
			File file = filePath.toFile();
			ImageConverter<File, String> converter = new ImageToBase64();
			String base64 = null;
			base64 = converter.convert(file);
			return base64;
		} catch (Exception e) {
			throw new RuntimeException("가수파일 전송 실패", e);
		}
	}

	@Builder
	public Musics(Long id, String title, String artist, String album, String filePath, String genre1, String genre2,
			String tag, String time, String lyrics, Long hit) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.filePath = filePath;
		this.genre1 = genre1;
		this.genre2 = genre2;
		this.tag = tag;
		this.time = time;
		this.lyrics = lyrics;
		this.hit = hit;
	}

	// jpa 에서 등록시 초기값 0 설정
	@PrePersist
	public void initializeHit() {
		if (hit == null) {
			hit = 0L;
		}
	}

}
