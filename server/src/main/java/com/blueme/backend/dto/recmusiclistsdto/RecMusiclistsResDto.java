package com.blueme.backend.dto.recmusiclistsdto;

import com.blueme.backend.model.entity.RecMusiclists;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecMusiclistsResDto {

  private Long recMusiclistId;

  private String title;

  private String reason;

  private String img;

  public RecMusiclistsResDto(RecMusiclists recMusiclist, String img) {
    this.recMusiclistId = recMusiclist.getId();
    this.title = recMusiclist.getTitle();
    this.reason = recMusiclist.getReason();
    this.img = img;
  }

}
