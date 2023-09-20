package com.blueme.backend.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 태그에 관련된 감정 enum 클래스입니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-20
 */
public enum EmotionState {
  BREAKUP("헤어짐"),
  SULKING("심술"),
  TIRED("tired"),
  SAD("슬픔"),
  SAD_ENG("sad"),
  DEPRESSED("depressed"),
  ANXIOUS("불안"), GLOOMY("우울"), RAGE("rage"),
  UNREST("unrest"), MISSING("그리움"),
  VICIOUS("viciously"),
  CONFESS("고백"),
  HAPPY("신남"),
  HAPPY_ENG("happy"),
  BEAT("설렘"),
  VALUABLE("소중"),
  FLUTTER("flutter");

  private String tag;

  EmotionState(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return this.tag;
  }

  private static final Map<String, EmotionState> lookup = new HashMap<>();

  static {
    for (EmotionState e : EmotionState.values()) {
      lookup.put(e.getTag(), e);
    }
  }

  public static EmotionState get(String tagName) {
    return lookup.get(tagName);
  }
}
