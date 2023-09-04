package com.blueme.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Music{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="music_id")
	private Long id;
	
	// 테스트용
	private String title;
	
	private String artist;
	
	private String album;
	
	private String filePath;
	
	@Builder
	public Music(String title, String artist, String album, String filePath) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.filePath = filePath;
	}
	
}
