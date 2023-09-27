package com.blueme.backend.dto.themesdto;

import java.util.List;

import com.blueme.backend.model.entity.Themes;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ThemeSaveReqDto {

  private String title;

  private String content;

  private List<String> musicIds;

  private String selectedTagId;

  public Themes toEntityThemes(String title, String content, String selectedTagId) {
    return Themes.builder().title(title).content(content).build();
  }

}
