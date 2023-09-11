package com.blueme.backend.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class Themes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="theme_id")
	private Long id;
	
	private String title;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="theme_id")
	private List<ThemeMusiclists> themeMusicList;
	
	private String content;

	private String themeImgPath;
	
	@Builder
	public Themes(String title, String content, List<ThemeMusiclists> themeMusicList, String themeImgPath) {
		this.title = title;
		this.content = content;
		this.themeMusicList = themeMusicList;
		this.themeImgPath = themeImgPath;
	}
	
}
