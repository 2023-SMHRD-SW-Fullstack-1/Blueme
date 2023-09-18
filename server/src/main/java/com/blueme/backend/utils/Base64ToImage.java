package com.blueme.backend.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

/**
 * Base64ToImage 클래스는 Base64로 인코딩된 문자열을 이미지 파일로 변환하는 유틸리티 클래스입니다.
 * <p>
 * 이 클래스는 Base64로 인코딩된 문자열을 디코딩하여 지정된 폴더에 이미지 파일로 저장하는 기능을 제공합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-18
 */
public class Base64ToImage {
  private final String STORAGE_FOLDER = "/usr/blueme/saveThemeImg/";

  /**
   * Base64로 인코딩된 문자열을 이미지 파일로 변환하고, 그 경로를 반환합니다.
   *
   * @param base64String 이미지 데이터가 포함된 Base64로 인코딩된 문자열
   * @return 생성된 이미지 파일의 경로 (String)
   * @throws IllegalArgumentException base64String이 비어 있거나 적합하지 않은 경우 발생
   * @throws RuntimeException         파일 저장에 실패한 경우 발생
   */
  public String convertBase64ToImage(String base64String) {
    if (base64String.isEmpty()) {
      throw new IllegalArgumentException("파일변환에 적합한 base64 문자열이 아닙니다.");
    }
    try {
      String fileNameWithUUID = UUID.randomUUID().toString();
      byte[] decodedBytes = Base64.getDecoder().decode(base64String);
      Path path = Paths.get(STORAGE_FOLDER + fileNameWithUUID + ".jpg");
      Files.write(path, decodedBytes);
      return path.toString();
    } catch (IOException e) {
      throw new RuntimeException("Failed to store the file", e);
    }
  }
}
