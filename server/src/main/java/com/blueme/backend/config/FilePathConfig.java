package com.blueme.backend.config;

import org.springframework.context.annotation.Configuration;

/**
 * FilePathConfig는 파일경로를 지정하는 설정클래스입니다.
 * <p>
 * 이 클래스에서는 음악파일 경로, 재킷사진 경로, 가수사진경로, 테마사진 경로를 설정합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.1
 * @since 2023-09-13
 */
@Configuration
public class FilePathConfig {
  /*
   * 개발경로
   * public static final String MUSIC_PATH = "\\usr\\blueme\\musics\\";
   * public static final String JACKET_PATH = "\\usr\\blueme\\jackets\\";
   * public static final String ARTIST_PATH = "\\usr\\blueme\\artists\\";
   * public static final String THEMES_IMG_PATH = "C:\\";
   */

  // 배포경로
  public static final String MUSIC_PATH = "/home/ubuntu/assets/musics/";
  public static final String JACKET_PATH = "/home/ubuntu/assets/jackets/";
  public static final String ARTIST_PATH = "/home/ubuntu/assets/artists/";
  public static final String THEMES_IMG_PATH = "";

}
