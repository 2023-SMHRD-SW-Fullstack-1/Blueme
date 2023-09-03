package com.blueme.backend.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Music {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="music_id")
	private Long id;
	
	
	
}
