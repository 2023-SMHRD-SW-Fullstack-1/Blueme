package com.blueme.backend.dto.recmusiclistsdto;

import java.util.List;
import java.util.stream.Collectors;

import com.blueme.backend.model.entity.RecMusiclists;

import lombok.Getter;

/*
 * 작성자 : 김혁
 * 작성일 : 2023-09-14
 * 설명   : 추천리스트 상세조회 DTO
 */

@Getter
public class RecMusiclistsSelectDetailResDto {
  private Long recMusiclistId;
  private String recMusiclistTitle;
  private String recMusiclistReason;
  List<RecMusiclistsRecent10DetailResDto> recMusiclistsRecent10detail;

  public RecMusiclistsSelectDetailResDto(RecMusiclists recMusiclist) {
    this.recMusiclistId = recMusiclist.getId();
    this.recMusiclistTitle = recMusiclist.getTitle();
    this.recMusiclistReason = recMusiclist.getReason();
    this.recMusiclistsRecent10detail = recMusiclist.getRecMusicListDetail().stream()
        .map(RecMusiclistsRecent10DetailResDto::new).collect(Collectors.toList());
  }
}
