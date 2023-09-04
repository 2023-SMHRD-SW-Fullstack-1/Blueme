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
public class Theme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="theme_id")
	private Long id;
	
	private String title;
	
	private String content;
	
	@Builder
	public Theme(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
}
