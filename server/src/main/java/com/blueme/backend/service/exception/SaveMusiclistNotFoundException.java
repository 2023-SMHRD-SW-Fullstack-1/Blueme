package com.blueme.backend.service.exception;

/*
 * 작성자: 김혁
 * 날짜(수정포함): 2023-09-14
 * 설명: 저장된음악리스트 NotFoundException
 */

public class SaveMusiclistNotFoundException extends RuntimeException {
  private Long saveMusiclistId;

  public SaveMusiclistNotFoundException(Long saveMusiclistId) {
    super("Could not find saveMusiclist with id: " + saveMusiclistId);
    this.saveMusiclistId = saveMusiclistId;
  }

  public Long getSaveMusiclistId() {
    return this.saveMusiclistId;
  }
}