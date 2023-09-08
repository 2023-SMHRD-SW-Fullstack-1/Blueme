package com.blueme.backend.dto.likemusicsDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikemusicIsSaveReqDto {
  private String userId;
  private String musicId;
}

