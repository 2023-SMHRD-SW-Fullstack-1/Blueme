package com.blueme.backend.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 음악 태그에 관련된 계절 enum 클래스입니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-20
 */
public enum Season {
  SPRING("봄"),
  SPRING_ENG("spring"),
  SUMMER("여름"),
  SUMMER_ENG("summer"),
  FALL("가을"),
  FALL_ENG("autumn"),
  WINTER("겨울"),
  WINTER_ENG("winter"),
  CHRISTMAS("크리스마스");

  private String tag;

  Season(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return this.tag;
  }

  private static final Map<String, Season> lookup = new HashMap<>();

  static {
    for (Season s : Season.values()) {
      lookup.put(s.getTag(), s);
    }
  }

  public static String getTag(String tagName) {
    return lookup.get(tagName).getTag();
  }
}
