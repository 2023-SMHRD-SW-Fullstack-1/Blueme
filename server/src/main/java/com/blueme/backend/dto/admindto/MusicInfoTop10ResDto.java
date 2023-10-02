package com.blueme.backend.dto.admindto;

import com.blueme.backend.model.entity.Musics;

import lombok.Getter;

@Getter
public class MusicInfoTop10ResDto {
  private String musicId;
  private String title;
  private String hit;
  private String img;
  private String genre;
  private String artist;

  public MusicInfoTop10ResDto(Musics music) {
    this.musicId = music.getId().toString();
    this.title = music.getTitle();
    this.hit = music.getHit().toString();
    this.img = music.getJacketFile();
    this.genre = music.getGenre1();
    this.artist = music.getArtist();
  }
}
