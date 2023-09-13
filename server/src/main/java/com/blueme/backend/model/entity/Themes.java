package com.blueme.backend.model.entity;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.blueme.backend.config.FilePathConfig;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

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
	@Column(name = "theme_id")
	private Long id;
	private String title;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "theme_id")
	private List<ThemeMusiclists> themeMusicList;
	private String content;
	private String themeImgPath;

	// 파일변환
	@Transient
	public String getThemeImgFile() {
		try {
			Path filePath = Paths.get(FilePathConfig.THEMES_IMG_PATH + themeImgPath);
			File file = filePath.toFile();
			ImageConverter<File, String> converter = new ImageToBase64();
			String base64 = null;
			base64 = converter.convert(file);
			return base64;
		} catch (Exception e) {
			throw new RuntimeException("테마이미지 전송 실패", e);
		}
	}

	@Builder
	public Themes(String title, String content, List<ThemeMusiclists> themeMusicList, String themeImgPath) {
		this.title = title;
		this.content = content;
		this.themeMusicList = themeMusicList;
		this.themeImgPath = themeImgPath;
	}

}
