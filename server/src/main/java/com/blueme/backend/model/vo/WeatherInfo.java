package com.blueme.backend.model.vo;

import java.util.List;
import lombok.Data;
import lombok.Getter;

/*
작성자: 김혁
날짜(수정포함): 2023-09-12
설명: 날씨정보 VO
*/

@Data
public class WeatherInfo {

  private Coord coord;
  private List<Weather> weather;
  private String base;
  private Main main;
  private int visibility;
  private Wind wind;
  private Clouds clouds;
  private long dt;
  private Sys sys;
  private int timezone;
  private long id;
  private String name;
  private int cod;

  public static class Coord {

    double lon, lat;
  }

  @Data
  public static class Weather {

    int id;
    String main, description, icon;
  }

  @Data
  public static class Main {

    double temp, feels_like, temp_min, temp_max;
    int pressure, humidity;
    int sea_level, grnd_level;
  }

  public static class Wind {

    double speed;
    int deg;
    double gust;
  }

  public static class Clouds {

    int all;
  }

  public static class Sys {

    String country;
    long sunrise, sunset;
  }
}
