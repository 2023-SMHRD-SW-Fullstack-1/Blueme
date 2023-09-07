package com.blueme.backend.dto.musicdto;

import com.blueme.backend.model.entity.Musics;

import lombok.Getter;

@Getter
public class MusicInfoResDto {

  public Long musicId;

  private String title;
  private String album;
  private String artist;
  private String genre1;
  private String genre2;
  private String lyrics;
  private String tag;
  private String time;
  private Long hit;
  private String img;
  

  public MusicInfoResDto(Musics music, String imgBase64){
    this.musicId = music.getId();
    this.title = music.getTitle();
    this.album = music.getAlbum();
    this.artist = music.getArtist();
    this.genre1 = music.getGenre1();
    this.genre2 = music.getGenre2();
    this.lyrics = music.getLyrics();
    this.tag = music.getTag();
    this.time = music.getTime();
    this.hit = music.getHit();
    this.img = imgBase64;
  }


}
