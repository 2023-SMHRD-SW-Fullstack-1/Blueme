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
public class Artists {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="artist_id")
	private Long id;
	
	@Column(length=8, nullable=false)
	private String name;
	
	@Column(length=20)
	private String type;
	
	private String artist_img;

	@Builder
	public Artists(String name, String type, String artist_img) {
		this.name = name;
		this.type = type;
		this.artist_img = artist_img;
	}
	
	


}
