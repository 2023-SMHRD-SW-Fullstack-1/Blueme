package com.blueme.backend.config;

import org.springframework.context.annotation.Configuration;

/*
작성자: 김혁
날짜(수정포함): 2023-09-13
설명: 파일 경로 설정파일
*/

@Configuration
public class FilePathConfig {
  public static final String MUSIC_PATH = "\\usr\\blueme\\musics\\";
  public static final String JACKET_PATH = "\\usr\\blueme\\jackets\\";
  public static final String ARTIST_PATH = "\\usr\\blueme\\artists\\";
  public static final String THEMES_IMG_PATH = "C:\\";
}
