package com.blueme.backend.dto.recmusiclistsdto;

import java.util.List;
import java.util.stream.Collectors;

import com.blueme.backend.model.entity.RecMusiclistDetails;
import com.blueme.backend.model.entity.RecMusiclists;

import lombok.Getter;

/*
 * 작성자: 김혁
 * 작성일: 2023-09-14
 * 설명 : 최근 추천목록 DTO
 */

@Getter
public class RecMusiclistsRecent10ResDto {

  private Long RecMusiclistId;
  private String RecMusiclistTitle;
  private String RecMusiclistReason;
  private String img;
  // private List<RecMusiclistsRecent10DetailResDto>
  // recMusiclistsRecent10DetailResDtos;

  public RecMusiclistsRecent10ResDto(RecMusiclists recMusiclist) {
    this.RecMusiclistId = recMusiclist.getId();
    this.RecMusiclistTitle = recMusiclist.getTitle();
    this.RecMusiclistReason = recMusiclist.getReason();
    this.img = recMusiclist.getRecMusicListDetail().get(0).getMusic().getJacketFile();
    // this.recMusiclistsRecent10DetailResDtos =
    // recMusiclist.getRecMusicListDetail()
    // .stream().map(RecMusiclistsRecent10DetailResDto::new).collect(Collectors.toList());
  }
}
