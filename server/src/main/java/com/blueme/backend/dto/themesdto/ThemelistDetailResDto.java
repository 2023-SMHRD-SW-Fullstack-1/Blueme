package com.blueme.backend.dto.themesdto;

import java.util.List;

import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.entity.ThemeMusiclists;
import com.blueme.backend.model.entity.Themes;

import lombok.Getter;

@Getter
public class ThemelistDetailResDto {
  Long themeId;

  String title;

  String content;

  Musics[] musics; 

  List<ThemeMusiclists> themeMusicList;

  //List<ThemeMusiclists> themeMusicList;

  public ThemelistDetailResDto(Themes theme) {
    this.themeId = theme.getId();
    this.title = theme.getTitle();
    this.content = theme.getContent();
    this.themeMusicList =  theme.getThemeMusicList();
    //this.themeMusicList = theme.getThemeMusicList();
    //this.musics = theme.getThemeMusicList()
  }
}
