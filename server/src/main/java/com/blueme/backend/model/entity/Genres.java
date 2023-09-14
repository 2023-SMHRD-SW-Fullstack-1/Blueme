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
public class Genres {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="genre_id")
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	
	private String genre_file_path;

	@Builder
	public Genres(String name,String genre_file_path) {
		this.name = name;
		this.genre_file_path=genre_file_path;
	}
	
	
}
