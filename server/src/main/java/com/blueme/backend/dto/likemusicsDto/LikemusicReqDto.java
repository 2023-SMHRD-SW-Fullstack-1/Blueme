package com.blueme.backend.dto.likemusicsDto;

import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikemusicReqDto {
  
  private String userId;

  private String musicId;
  
  public Users toEntityUser() {
    return Users.builder().id(Long.parseLong(userId)).build();
  }

  public Musics toEntityMusic() {
    return Musics.builder().id(Long.parseLong(musicId)).build();
  }
}
