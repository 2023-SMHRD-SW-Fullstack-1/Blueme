package com.blueme.backend.dto.savedMusiclistsdto;

import java.util.List;

import com.blueme.backend.model.entity.SavedMusiclists;

import lombok.Getter;

/*
작성자: 김혁
날짜(수정포함): 2023-09-14
설명: 저장된음악 조회 DTO
*/

@Getter
public class SavedMusiclistsResDto {
  private Long savedMusiclistId;
  private String title;
  private String img;

  public SavedMusiclistsResDto(SavedMusiclists savedMusiclist) {
    this.savedMusiclistId = savedMusiclist.getId();
    this.title = savedMusiclist.getTitle();
    this.img = savedMusiclist.getSavedMusiclistDetails().get(0).getMusic().getJacketFile();
  }
}
