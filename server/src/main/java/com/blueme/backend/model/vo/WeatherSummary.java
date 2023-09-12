package com.blueme.backend.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Value;

/*
작성자: 김혁
날짜(수정포함): 2023-09-12
설명: 사용하는 날씨정보 VO
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
