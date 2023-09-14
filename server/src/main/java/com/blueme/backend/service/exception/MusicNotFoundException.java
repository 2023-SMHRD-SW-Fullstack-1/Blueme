package com.blueme.backend.service.exception;

/*
 * 작성자: 김혁
 * 날짜(수정포함): 2023-09-13
 * 설명: 음악 NotFoundException
 */

public class MusicNotFoundException extends RuntimeException {
  private Long musicId;

  public MusicNotFoundException(Long musicId) {
    super("Could not find music with id: " + musicId);
    this.musicId = musicId;
  }

  public Long getMusicId() {
    return this.musicId;
  }
}