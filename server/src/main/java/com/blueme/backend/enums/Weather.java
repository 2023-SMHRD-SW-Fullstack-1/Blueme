package com.blueme.backend.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 음악 태그에 관련된 날씨 enum 클래스입니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-20
 */
public enum Weather {
  SNOWSTORM("눈보라"),
  RAINY("비"),
  HOT("더위"),
  HOT_ENG("hot"),
  SUNNY("햇살"),
  WINDY_ENG("wind"),
  WINDY("바람"),
  CLOUDY("흐림"),
  CLOUDY_ENG("cloud"),
  RAIN("rain"),
  SUN("sun"),
  COLD("추위"),
  COLD_ENG("cold"),
  SNOW_ENG("snow")

  ;

  private String tag;

  Weather(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return this.tag;
  }

  private static final Map<String, Weather> lookup = new HashMap<>();

  static {
    for (Weather w : Weather.values()) {
      lookup.put(w.getTag(), w);
    }
  }

  public static Weather get(String tagName) {
    return lookup.get(tagName);
  }
}