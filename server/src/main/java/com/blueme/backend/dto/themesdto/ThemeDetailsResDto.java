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
  private String genre1;
  private String genre2;
  private String lyrics;
  private String tag;
  private String time;
  private String img;

  public ThemeDetailsResDto(Themes theme, ThemeMusiclists themeMusiclist, String img) {
    this.themeId = theme.getId();
    this.themeMusiclistId = themeMusiclist.getId();
    this.musicId = themeMusiclist.getMusic().getId();
    this.title = themeMusiclist.getMusic().getTitle();
    this.album = themeMusiclist.getMusic().getAlbum();
    this.artist = themeMusiclist.getMusic().getArtist();
    this.genre1 = themeMusiclist.getMusic().getGenre1();
    this.genre2 = themeMusiclist.getMusic().getGenre2();
    this.lyrics = themeMusiclist.getMusic().getLyrics();
    this.tag = themeMusiclist.getMusic().getTag();
    this.time =  themeMusiclist.getMusic().getTime();
    this.img = img;
  }
}
