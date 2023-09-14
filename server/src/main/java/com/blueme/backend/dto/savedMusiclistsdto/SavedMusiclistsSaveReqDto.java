package com.blueme.backend.dto.savedMusiclistsdto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
