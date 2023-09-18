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

/**
 * 음악 엔터티 클래스입니다.
 * <p>
 * 이 클래스는 음악의 제목, 아티스트, 앨범, 파일 경로 등의 정보를 저장합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0.0
 * @since 2023-09-06
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

	/**
	 * 재킷 이미지 파일의 경로를 Base64 문자열로 변환하여 반환하는 메서드입니다.
	 * <p>
	 * 이 메서드는 파일 시스템에서 재킷 이미지 파일을 읽어 Base64 문자열로 변환합니다.
	 * 이 메서드는 DB에 영속화되지 않습니다.
	 * </p>
	 * 
	 * @return 재킷 이미지 파일의 Base64 문자열
	 * @throws RuntimeException 재킷파일 전송 실패 시 발생
	 */
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

	/**
	 * 아티스트 이미지 파일의 경로를 Base64 문자열로 변환하여 반환하는 메서드입니다.
	 * <p>
	 * 이 메서드는 파일 시스템에서 아티스트 이미지 파일을 읽어 Base64 문자열로 변환합니다.
	 * 이 메서드는 DB에 영속화되지 않습니다.
	 * </p>
	 * 
	 * @return 아티스트 이미지 파일의 Base64 문자열
	 * @throws RuntimeException 가수파일 전송 실패 시 발생
	 */
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

	/**
	 * JPA가 엔터티를 저장하기 전에 'hit' 필드의 초기값을 설정하는 메서드입니다.
	 * <p>
	 * 만약 'hit' 필드가 null이면, 이 메서드는 그 값을 0으로 설정합니다.
	 * </p>
	 */
	@PrePersist
	public void initializeHit() {
		if (hit == null) {
			hit = 0L;
		}
	}

}
