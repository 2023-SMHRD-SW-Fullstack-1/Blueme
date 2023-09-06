package com.blueme.backend.dto.themesdto;

import com.blueme.backend.model.entity.Themes;

import lombok.Getter;

@Getter
public class ThemelistResDto {

  Long themeId;

  String title;

  String content;

  public ThemelistResDto(Themes theme) {
    this.themeId = theme.getId();
    this.title = theme.getTitle();
    this.content = theme.getContent();
  }
  
}
