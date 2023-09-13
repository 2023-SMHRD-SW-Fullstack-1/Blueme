package com.blueme.backend.dto.likemusicsDto;

import com.blueme.backend.model.entity.Musics;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikemusicReqDto {

  private String userId;

  private String musicId;

  public Musics toEntityMusic() {
    return Musics.builder().id(Long.parseLong(musicId)).build();
  }
}
