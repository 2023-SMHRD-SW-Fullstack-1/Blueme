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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.blueme.backend.config.FilePathConfig;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 테마 엔터티입니다.
 * <p>
 * 이 클래스는 테마목록을 관리합니다.
 * 테마 엔터티는 여러개의 테마리스트를 가질수 있습니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-06
 */
@Getter
@NoArgsConstructor
@Entity
@Setter
public class Themes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "theme_id")
	private Long id;

	@Column(nullable = false, length = 100)
	private String title;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "theme_id")
	private List<ThemeMusiclists> themeMusicList;
	@Column(length = 200)
	private String content;
	@Column(nullable = false, length = 200)
	private String themeImgPath;

	@ManyToOne
	@JoinColumn(name = "theme_tag_id")
	private ThemeTags themeTags;

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
	public Themes(String title, String content, List<ThemeMusiclists> themeMusicList, String themeImgPath,
			ThemeTags themeTag) {
		this.title = title;
		this.content = content;
		this.themeMusicList = themeMusicList;
		this.themeImgPath = themeImgPath;
		this.themeTags = themeTag;
	}

}
