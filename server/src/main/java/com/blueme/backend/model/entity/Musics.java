package com.blueme.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Musics{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="music_id")
	private Long id;

	private String title;
	
	private String artist;
	
	private String album;
	
	private String filePath;
	
	private String jacketFilePath;
	
	private String genre1;
	
	private String genre2;
	
	private String tag;
	
	private String time;

	private Long hit;
	
	@Lob
	private String lyrics;
	
	@Builder
	public Musics(String title, String artist, String album, String filePath
			, String genre1, String genre2, String tag, String time, String lyrics, Long hit) {
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
