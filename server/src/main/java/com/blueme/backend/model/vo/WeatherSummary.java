package com.blueme.backend.model.vo;

import lombok.Value;

/**
 * 현재 날씨정보를 담고있는 VO입니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-12
 */
@Value
public class WeatherSummary {

  private String condition;
  private String temp;
  private String humidity;

  public WeatherSummary(String condition, String temp, String humidity) {
    this.condition = condition;
    this.temp = temp;
    this.humidity = humidity;
  }
}
