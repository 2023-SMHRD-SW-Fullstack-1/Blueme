package com.blueme.backend.dto.savedMusiclistsdto;

import java.util.List;
import java.util.stream.Collectors;

import com.blueme.backend.model.entity.SavedMusiclists;

import lombok.Getter;

/*
작성자: 김혁
날짜(수정포함): 2023-09-04
설명: 저장음악리스트 상세조회 dto
*/

@Getter
public class SavedMusiclistsGetResDto {
  private Long savedMusiclistId;
  private String title;
  List<SavedMusiclistDetailsResDto> savedMusiclistDetailsResDto;

  public SavedMusiclistsGetResDto(SavedMusiclists savedMusiclist) {
    this.savedMusiclistId = savedMusiclist.getId();
    this.title = savedMusiclist.getTitle();
    this.savedMusiclistDetailsResDto = savedMusiclist.getSavedMusiclistDetails().stream()
        .map(SavedMusiclistDetailsResDto::new).collect(Collectors.toList());

  }
}
