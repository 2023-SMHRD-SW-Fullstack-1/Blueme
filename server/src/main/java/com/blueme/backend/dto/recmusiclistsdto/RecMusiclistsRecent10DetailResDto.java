package com.blueme.backend.dto.recmusiclistsdto;

import com.blueme.backend.model.entity.RecMusiclistDetails;

import lombok.Getter;

@Getter
public class RecMusiclistsRecent10DetailResDto {

  public Long recMusiclistDetailId;
  public Long musicId;
  public String title;
  public String artist;
  public String album;

  public RecMusiclistsRecent10DetailResDto(RecMusiclistDetails detail) {

  }

}
