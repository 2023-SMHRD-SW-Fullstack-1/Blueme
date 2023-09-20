package com.blueme.backend.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 음악 태그에 관련된 시간 enum 클래스입니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-20
 */

public enum TimeOfDay {
  MORNING("아침"),
  MORNING_ENG("morning"),
  DAWN("새벽"),
  DAWN_ENG("DAWN"),
  EVENING("저녁"),
  EVENING_ENG("evening"),
  AFTERNOON("오후"),
  AFTERNOON_ENG("afternoon"),
  FORENOON("오전"),
  LUNCH("점심"),
  LUNCH_ENG("lunch"),
  NIGHT("밤"),
  NIGHT_ENG("night");

  private String tag;

  TimeOfDay(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return this.tag;
  }

  private static final Map<String, TimeOfDay> lookup = new HashMap<>();

  static {
    for (TimeOfDay t : TimeOfDay.values()) {
      lookup.put(t.getTag(), t);
    }
  }

  public static TimeOfDay get(String tagName) {
    return lookup.get(tagName);
  }
}
