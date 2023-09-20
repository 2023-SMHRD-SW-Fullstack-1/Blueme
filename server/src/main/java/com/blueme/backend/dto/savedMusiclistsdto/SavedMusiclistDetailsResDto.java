package com.blueme.backend.dto.savedMusiclistsdto;

import com.blueme.backend.model.entity.SavedMusiclistDetails;

import lombok.Getter;

@Getter
public class SavedMusiclistDetailsResDto {

  private Long savedMusiclistsDetailId;
  private Long musicId;
  private String img;
  private String musicTitle;
  private String musicArtist;
  private String musicAlbum;
  private String musicGenre;
  private String time;

  public SavedMusiclistDetailsResDto(SavedMusiclistDetails detail) {
    this.savedMusiclistsDetailId = detail.getId();
    this.musicId = detail.getMusic().getId();
    this.img = detail.getMusic().getJacketFile();
    this.musicTitle = detail.getMusic().getTitle();
    this.musicArtist = detail.getMusic().getArtist();
    this.musicAlbum = detail.getMusic().getAlbum();
    this.musicGenre = detail.getMusic().getGenre1();
    this.time = detail.getMusic().getTime();
  }

}
