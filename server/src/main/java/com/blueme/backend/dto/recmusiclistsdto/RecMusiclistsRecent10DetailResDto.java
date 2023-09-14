package com.blueme.backend.dto.recmusiclistsdto;

import com.blueme.backend.model.entity.RecMusiclistDetails;

import lombok.Getter;

/*
 * 작성자 : 김혁
 * 작성일 : 2023-09-14
 * 설명   : 추천리스트 상세조회 DTO
 */

@Getter
public class RecMusiclistsRecent10DetailResDto {

  public Long recMusiclistDetailId;
  public Long musicId;
  public String title;
  public String artist;
  public String album;
  public String img;

  public RecMusiclistsRecent10DetailResDto(RecMusiclistDetails detail) {
    this.recMusiclistDetailId = detail.getId();
    this.musicId = detail.getMusic().getId();
    this.title = detail.getMusic().getTitle();
    this.artist = detail.getMusic().getArtist();
    this.album = detail.getMusic().getAlbum();
    this.img = detail.getMusic().getJacketFile();
  }

}
