package com.blueme.backend.dto.searchdto;

import com.blueme.backend.model.entity.Musics;

import lombok.Getter;

@Getter
public class SearchsResDto {
  private Long musicId;
  private String title;
  private String artist;
  private String img;

  public SearchsResDto(Musics music) {
    this.musicId = music.getId();
    this.title = music.getTitle();
    this.artist = music.getArtist();
    this.img = music.getJacketFile();
  }
}
