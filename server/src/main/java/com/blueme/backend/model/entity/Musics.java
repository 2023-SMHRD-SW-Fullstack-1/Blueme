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
	
	@Builder
	public Musics(Long id,String title, String artist, String album, String filePath
			, String genre1, String genre2, String tag, String time, String lyrics, Long hit) {
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
