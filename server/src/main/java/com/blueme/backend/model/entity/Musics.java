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
public class Musics{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="music_id")
	private Long id;
	
	// 테스트용
	private String title;
	
	private String artist;
	
	private String album;
	
	private String filePath;
	
	private String genre;
	
	private String bpm;
	
	private String mood;
	
	private String year;
	
	@Builder
	public Musics(String title, String artist, String album, String filePath
			, String genre, String bpm, String mood, String year) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.filePath = filePath;
		this.genre = genre;
		this.bpm = bpm;
		this.mood = mood;
		this.year = year;
	}
	
}
