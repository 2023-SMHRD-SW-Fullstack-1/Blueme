package com.blueme.backend.dto.themesdto;

import com.blueme.backend.model.entity.ThemeTags;

import lombok.Getter;

@Getter
public class TagDetailsResDto {
  private Long tagId;
  private String name;

  public TagDetailsResDto(ThemeTags themeTag) {
    this.tagId = themeTag.getId();
    this.name = themeTag.getName();
  }
}
