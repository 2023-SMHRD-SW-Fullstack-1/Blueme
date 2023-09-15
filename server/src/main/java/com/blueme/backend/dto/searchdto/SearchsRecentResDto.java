package com.blueme.backend.dto.searchdto;

import com.blueme.backend.model.entity.Searchs;

import lombok.Getter;

@Getter
public class SearchsRecentResDto {
  private Long searchId;
  private Long musicId;
  private String title;
  private String artist;
  private String img;

  public SearchsRecentResDto(Searchs search) {
    this.searchId = search.getId();
    this.musicId = search.getMusic().getId();
    this.title = search.getMusic().getTitle();
    this.artist = search.getMusic().getArtist();
    this.img = search.getMusic().getJacketFile();
  }

}
