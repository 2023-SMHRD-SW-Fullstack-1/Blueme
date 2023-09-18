package com.blueme.backend.dto.savedMusiclistsdto;

import java.io.File;
import java.util.List;

import com.blueme.backend.utils.Base64ToImage;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자가 저장한 음악 목록을 생성하기 위한 요청 데이터 전송 객체(DTO).
 * 이 DTO는 클라이언트로부터 받은 정보를 서버로 전달하는 역할을 한다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-14
 */
@Getter
@NoArgsConstructor
public class SavedMusiclistsSaveReqDto {
  private String userId;
  private String title;
  private List<String> musicIds;
  private String image;

  public Long parsedUserId() {
    return Long.parseLong(userId);
  }

}
