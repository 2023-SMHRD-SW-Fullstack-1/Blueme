package com.blueme.backend.dto.recmusiclistsdto;

import com.blueme.backend.model.entity.RecMusiclistDetails;

import lombok.Getter;

@Getter
public class RecMusiclistsDetailResDto {

  private Long recMusiclistDetailId;
  private Long musicId;
  private String img;
  private String musicTitle;
  private String musicArtist;
  private String musicAlbum;
  private String musicGenre;
  private String musicTime;

  public RecMusiclistsDetailResDto(RecMusiclistDetails detailResDto) {
    this.recMusiclistDetailId = detailResDto.getId();
    this.musicId = detailResDto.getMusic().getId();
    this.img = detailResDto.getMusic().getJacketFile();
    this.musicTitle = detailResDto.getMusic().getTitle();
    this.musicAlbum = detailResDto.getMusic().getArtist();
    this.musicAlbum = detailResDto.getMusic().getAlbum();
    this.musicGenre = detailResDto.getMusic().getGenre1();
    this.musicArtist = detailResDto.getMusic().getArtist();
    this.musicTime = detailResDto.getMusic().getTime();
  }

}
