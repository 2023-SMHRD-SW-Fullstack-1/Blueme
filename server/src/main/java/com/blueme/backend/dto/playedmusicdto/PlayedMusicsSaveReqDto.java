package com.blueme.backend.dto.playedmusicdto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlayedMusicsSaveReqDto {
  private String userId;

  private String musicId;

  public Long getParsedUserId() {
    return Long.parseLong(userId);
  }

  public Long getParsedMusicId() {
    return Long.parseLong(musicId);
  }
}
