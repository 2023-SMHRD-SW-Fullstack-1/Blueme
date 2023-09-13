package com.blueme.backend.dto.recmusiclistsdto;

import java.util.List;
import java.util.stream.Collectors;

import com.blueme.backend.model.entity.RecMusiclists;

import lombok.Getter;

/*
작성자: 김혁
날짜(수정포함): 2023-09-13
설명: 추천음악 조회 DTO
*/

@Getter
public class RecMusiclistsResDto {

  private Long recMusiclistId;

  private String title;

  private String reason;

  List<RecMusiclistsDetailResDto> recMusiclistDetails;

  public RecMusiclistsResDto(RecMusiclists recMusiclist) {
    this.recMusiclistId = recMusiclist.getId();
    this.title = recMusiclist.getTitle();
    this.reason = recMusiclist.getReason();
    this.recMusiclistDetails = recMusiclist.getRecMusicListDetail().stream()
        .map(recMusiclistDetail -> new RecMusiclistsDetailResDto(recMusiclistDetail)).collect(Collectors.toList());
  }

}
