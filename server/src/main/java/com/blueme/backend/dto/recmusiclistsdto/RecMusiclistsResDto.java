package com.blueme.backend.dto.recmusiclistsdto;

import java.util.List;

import com.blueme.backend.model.entity.RecMusiclists;

import lombok.Getter;

@Getter
public class RecMusiclistsResDto {

  private Long recMusiclistId;

  private String title;

  private String reason;

  List<RecMusiclistsDetailResDto> recMusiclistDetails;

  public RecMusiclistsResDto(RecMusiclists recMusiclist, List<RecMusiclistsDetailResDto> detailResDto) {
    this.recMusiclistId = recMusiclist.getId();
    this.title = recMusiclist.getTitle();
    this.reason = recMusiclist.getReason();
    this.recMusiclistDetails = detailResDto;
  }

}
