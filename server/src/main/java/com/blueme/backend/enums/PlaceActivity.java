package com.blueme.backend.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 음악 태그에 관련된 장소,활동 enum 클래스입니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-20
 */
public enum PlaceActivity {
  CAMPUS("캠퍼스"),
  DRIVING("운전"),
  DRIVING_ENG("drive"),
  COLLEGE("college"),
  STORE("store"), PARTY("파티"), PARTY_ENG("party"),
  RESTAURANT("레스토랑"), GYM("체육관"), GYM_ENG("gym"),
  SUBWAY("지하철"),
  SUBWAY_ENG("subway"),
  KARAOKE("노래방"),
  TRAVEL("여행"),
  TRAVEL_ENG("travel"),
  HOME("집"),
  HOME_ENG("home"),
  STUDY("공부"),
  CAFE("카페"),
  SEA("바다"),
  BEACH("beach");

  private String tag;

  PlaceActivity(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return this.tag;
  }

  private static final Map<String, PlaceActivity> lookup = new HashMap<>();

  static {
    for (PlaceActivity p : PlaceActivity.values()) {
      lookup.put(p.getTag(), p);
    }
  }

  public static PlaceActivity get(String tagName) {
    return lookup.get(tagName);
  }
}