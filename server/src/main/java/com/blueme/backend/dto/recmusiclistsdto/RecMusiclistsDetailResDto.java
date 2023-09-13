package com.blueme.backend.dto.recmusiclistsdto;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.blueme.backend.model.entity.RecMusiclistDetails;
import com.blueme.backend.model.entity.RecMusiclists;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;

import lombok.Getter;

@Getter
public class RecMusiclistsDetailResDto {
  private Long recMusiclistId;
  private String title;
  private String reason;
  List<RecMusiclistDetails> recMusicListDetail;

  public RecMusiclistsDetailResDto(RecMusiclists recMusiclist) {
    this.recMusiclistId = recMusiclist.getId();
    this.title = recMusiclist.getTitle();
    this.reason = recMusiclist.getReason();
    // this.recMusicListDetail = addImage(recMusiclist.getRecMusicListDetail());
    this.recMusicListDetail = recMusiclist.getRecMusicListDetail();
  }

  // base64로 이미지 변환하기
  public List<RecMusiclistDetails> addImage(List<RecMusiclistDetails> recMusicListDetail) {
    List<RecMusiclistDetails> result = recMusicListDetail;
    try {
      for (RecMusiclistDetails rmd : recMusicListDetail) {
        Path filePath = Paths.get("\\usr\\blueme\\jackets\\" + rmd.getMusic().getJacketFilePath() + ".jpg");
        File file = filePath.toFile();
        ImageConverter<File, String> converter = new ImageToBase64();
        String base64 = null;
        base64 = converter.convert(file);
        // rmd.getMusic().setJacketFilePath(base64);
      }
      return result;
    } catch (Exception e) {
      throw new RuntimeException("재킷파일 전송 실패", e);
    }

  }

}
