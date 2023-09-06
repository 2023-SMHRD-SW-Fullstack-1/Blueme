package com.blueme.backend.dto.themesdto;

import com.blueme.backend.model.entity.ThemeMusiclists;
import com.blueme.backend.model.entity.Themes;

import lombok.Getter;

@Getter
public class ThemeDetailsResDto {
  private Long themeId;

  private Long themeMusiclistId;

  private Long musicId;
  private String title;
  private String album;
  private String artist;
  private String bpm;
  private String genre;
  private String mood;
  private String year;

  public ThemeDetailsResDto(Themes theme, ThemeMusiclists themeMusiclist) {
    this.themeId = theme.getId();
    this.themeMusiclistId = themeMusiclist.getId();
    this.musicId = themeMusiclist.getMusic().getId();
    this.title = themeMusiclist.getMusic().getTitle();
    this.album = themeMusiclist.getMusic().getAlbum();
    this.artist = themeMusiclist.getMusic().getArtist();
    this.bpm = themeMusiclist.getMusic().getBpm();
    this.genre = themeMusiclist.getMusic().getGenre();
    this.mood = themeMusiclist.getMusic().getMood();
    this.year =  themeMusiclist.getMusic().getYear();

  }
}
