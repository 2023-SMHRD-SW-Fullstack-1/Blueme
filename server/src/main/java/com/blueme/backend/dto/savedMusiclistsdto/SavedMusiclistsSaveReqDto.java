package com.blueme.backend.dto.savedMusiclistsdto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
작성자: 김혁
날짜(수정포함): 2023-09-14
설명: 저장된음악리스트 조회 DTO
*/

@Getter
@NoArgsConstructor
public class SavedMusiclistsSaveReqDto {
  private String userId;
  private String title;
  private List<String> musicIds;

  public Long parsedUserId() {
    return Long.parseLong(userId);
  }
}
