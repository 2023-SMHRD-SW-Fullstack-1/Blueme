package com.blueme.backend.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.blueme.backend.api.client.WeatherAPIClient;
import com.blueme.backend.model.entity.HealthInfos;
import com.blueme.backend.model.repository.HealthInfosJpaRepository;
import com.blueme.backend.model.repository.RecMusicListsJpaRepository;
import com.blueme.backend.model.vo.WeatherInfo;
import com.blueme.backend.model.vo.WeatherSummary;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


/*
작성자: 김혁
날짜(수정포함): 2023-09-02
설명: 추천음악 관련 서비스
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class RecMusiclistsService {
  
  private final RecMusicListsJpaRepository recMusiclistsJpaRepository;
  private final HealthInfosJpaRepository healthInfosJpaRepository;
  private final WeatherAPIClient weatherAPIClient;

  public Long registerRecMusiclist(String userId){
    // 건강데이터 있는지 확인, 있으면 가장 최신의 건강데이터 1개 추출
    Optional<HealthInfos> healthInfo = healthInfosJpaRepository.findFirstByUserIdOrderByCreatedAtDesc(Long.parseLong(userId));
    WeatherSummary weatherSummary = null;

    if(healthInfo.isPresent()){ // 건강데이터가 존재한다면
      // 날씨 데이터 가져오기
      weatherAPIClient.getWeather(healthInfo.get().getLat(), healthInfo.get().getLon())
        .subscribe(weatherData -> {
          
          
  
          
        });
    }
    

    
    

    // 날씨 데이터 가져오기


    return 1L;
  }
  

}
