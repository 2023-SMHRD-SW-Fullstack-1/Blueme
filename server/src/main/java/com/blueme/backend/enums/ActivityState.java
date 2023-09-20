package com.blueme.backend.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 태그에 관련된 활동 enum 클래스입니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-20
 */
public enum ActivityState {
  REST("휴식"),
  REST_ENG("rest"),
  BUSINESS("사업"),
  EXCERSICE("운동"),
  STUDY("study");

  private String tag;

  ActivityState(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return this.tag;
  }

  private static final Map<String, ActivityState> lookup = new HashMap<>();

  static {
    for (ActivityState e : ActivityState.values()) {
      lookup.put(e.getTag(), e);
    }
  }

  public static ActivityState get(String tagName) {
    return lookup.get(tagName);
  }
}
