package com.blueme.backend.dto.recmusiclistsdto;

import com.blueme.backend.model.entity.RecMusiclistDetails;

import lombok.Getter;

/**
 * 추천리스트 상세조회DTO
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-14
 */
@Getter
public class RecMusiclistsRecent10DetailResDto {

  public Long recMusiclistDetailId;
  public Long musicId;
  public String title;
  public String artist;
  public String album;
  public String img;
  public String musicGenre;
  public String musicTime;

  public RecMusiclistsRecent10DetailResDto(RecMusiclistDetails detail) {
    this.recMusiclistDetailId = detail.getId();
    this.musicId = detail.getMusic().getId();
    this.title = detail.getMusic().getTitle();
    this.artist = detail.getMusic().getArtist();
    this.album = detail.getMusic().getAlbum();
    this.img = detail.getMusic().getJacketFile();
    this.musicGenre = detail.getMusic().getGenre1();
    this.musicTime = detail.getMusic().getTime();
  }

}
