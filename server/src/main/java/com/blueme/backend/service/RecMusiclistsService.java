package com.blueme.backend.service;

import com.blueme.backend.api.client.WeatherAPIClient;
import com.blueme.backend.dto.gptdto.ChatGptResDto;
import com.blueme.backend.dto.gptdto.QuestionReqDto;
import com.blueme.backend.dto.musiclistsdto.RecMusicListSaveDto;
import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsRecent10ResDto;
import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsResDto;
import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsSelectDetailResDto;
import com.blueme.backend.model.entity.HealthInfos;
import com.blueme.backend.model.entity.RecMusiclists;
import com.blueme.backend.model.repository.HealthInfosJpaRepository;
import com.blueme.backend.model.repository.RecMusicListsJpaRepository;
import com.blueme.backend.model.vo.WeatherInfo;
import com.blueme.backend.model.vo.WeatherSummary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
작성자: 김혁
날짜(수정포함): 2023-09-13
설명: 추천음악 관련 서비스
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class RecMusiclistsService {

  private final HealthInfosJpaRepository healthInfosJpaRepository;
  private final WeatherAPIClient weatherAPIClient;
  private final ChatGptService chatGptService;
  private final MusicListsService musicListsService;
  private final RecMusicListsJpaRepository recMusicListsJpaRepository;

  /*
   * get 최근추천목록 조회
   */
  @Transactional(readOnly = true)
  public List<RecMusiclistsRecent10ResDto> getRecent10RecMusiclists() {
    return recMusicListsJpaRepository.findTop10ByOrderByCreatedAtDesc().stream().map(RecMusiclistsRecent10ResDto::new)
        .collect(Collectors.toList());
  }

  /*
   * get 추천리스트 상세조회
   */
  @Transactional(readOnly = true)
  public RecMusiclistsSelectDetailResDto getRecMusiclistDetail(String recMusiclistId) {
    return new RecMusiclistsSelectDetailResDto(recMusicListsJpaRepository.findById(Long.parseLong(recMusiclistId))
        .orElseThrow(null));
  }

  /*
   * post 추천 음악등록(chatGPT)
   */
  @Transactional
  public Long registerRecMusiclist(String userIdStr) {
    Long userId = Long.parseLong(userIdStr);
    // 건강데이터 있는지 확인, 있으면 가장 최신의 건강데이터 1개 추출
    HealthInfos healthInfo = healthInfosJpaRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
    if (healthInfo == null) {
      return -1L;
    }
    WeatherSummary weatherSummary = getWeatherSummary(healthInfo);
    String question = chatGptService.makeQuestion(weatherSummary, healthInfo);
    ChatGptResDto response = chatGptService.askQuestion(new QuestionReqDto(question));
    return processChatGptResponse(response, userIdStr);
  }

  // 날씨 데이터 가져오기
  private WeatherSummary getWeatherSummary(HealthInfos healthInfo) {
    WeatherInfo weatherData = weatherAPIClient.getWeather(healthInfo.getLat(), healthInfo.getLon());
    String condition = weatherData.getWeather().get(0).getMain();
    String temp = Double.toString(weatherData.getMain().getTemp());
    String humidity = Integer.toString(weatherData.getMain().getHumidity());
    return new WeatherSummary(condition, temp, humidity);
  }

  // ChatGptResDto 에서 사용할 데이터 추출
  private Long processChatGptResponse(ChatGptResDto response, String userId) {
    String content = response.getChoices().get(0).getMessage().getContent();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      RecMusicListSaveDto recMusiclistSaveDto = objectMapper.readValue(content, RecMusicListSaveDto.class);
      recMusiclistSaveDto.setUserId(userId);
      return musicListsService.save(recMusiclistSaveDto);
    } catch (JsonProcessingException e) {
      log.error("jackson 저장 실패!", e);
      return -1L;
    }
  }

  /*
   * get (사용자에 해당하는) 최근 추천리스트 조회
   */
  @Transactional(readOnly = true)
  public RecMusiclistsResDto getRecentRecMusiclists(String userId) {
    RecMusiclists recMusicList = recMusicListsJpaRepository
        .findFirstByUserIdOrderByCreatedAtDesc(Long.parseLong(userId));
    return recMusicList == null ? null : new RecMusiclistsResDto(recMusicList);
  }

  @Transactional(readOnly = true)
  public List<RecMusiclistsResDto> getAllRecMusiclists(String userId) {
    return recMusicListsJpaRepository.findByUserId(Long.parseLong(userId)).stream().map(RecMusiclistsResDto::new)
        .collect(Collectors.toList());
  }

}
