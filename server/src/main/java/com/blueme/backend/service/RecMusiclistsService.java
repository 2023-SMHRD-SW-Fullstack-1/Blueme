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

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * RecMusiclistsService는 추천 음악 관련 서비스 클래스입니다.
 * <p>
 * 이 클래스에서는 추천 음악 목록의 조회, 상세 조회, 등록 기능을 제공합니다.
 * </p>
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-13
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

  /**
   * 특정 사용자의 최근 추천 음악 목록을 조회합니다.
   *
   * @return 최근 추천음악목록 10개 (RecMusiclistsRecent10ResDto 리스트)
   */
  @Transactional(readOnly = true)
  public List<RecMusiclistsRecent10ResDto> getRecent10RecMusiclists() {
    return recMusicListsJpaRepository.findTop10ByOrderByCreatedAtDesc().stream().map(RecMusiclistsRecent10ResDto::new)
        .collect(Collectors.toList());
  }

  /**
   * 특정 추천 음악 목록의 상세 정보를 조회합니다.
   *
   * @param recMusiclistId 조회하려는 추천 음악 목록의 ID (String)
   * @return 해당 추천 음악 목록의 상세 정보 (RecMusiclistsSelectDetailResDto)
   */
  @Transactional(readOnly = true)
  public RecMusiclistsSelectDetailResDto getRecMusiclistDetail(String recMusiclistId) {
    return new RecMusiclistsSelectDetailResDto(recMusicListsJpaRepository.findById(Long.parseLong(recMusiclistId))
        .orElseThrow(null));
  }

  /**
   * GPTAPI를 이용해 추천음악목록을 등록합니다.
   *
   * @param userIdStr 사용자 ID (String)
   * @return 추천음악목록의 ID (Long). 만약 건강 데이터가 없다면 null 반환
   */
  @Transactional
  public Long registerRecMusiclist(String userIdStr) {
    Long userId = Long.parseLong(userIdStr);
    HealthInfos healthInfo = healthInfosJpaRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
    if (healthInfo == null) {
      return null;
    }
    WeatherSummary weatherSummary = getWeatherSummary(healthInfo);
    String question = chatGptService.makeQuestion(weatherSummary, healthInfo);
    ChatGptResDto response = chatGptService.askQuestion(new QuestionReqDto(question));
    return processChatGptResponse(response, userIdStr);
  }

  // 현재 날씨 정보를 가져옵니다.
  private WeatherSummary getWeatherSummary(HealthInfos healthInfo) {
    WeatherInfo weatherData = weatherAPIClient.getWeather(healthInfo.getLat(), healthInfo.getLon());
    String condition = weatherData.getWeather().get(0).getMain();
    String temp = Double.toString(weatherData.getMain().getTemp());
    String humidity = Integer.toString(weatherData.getMain().getHumidity());
    return new WeatherSummary(condition, temp, humidity);
  }

  // ChatGptResDto 에서 사용할 데이터 추출합니다.
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

  /**
   * 특정 사용자의 최근 추천음악목록 1개를 조회합니다.
   *
   * @param userId 사용자 ID (String)
   * @return 추천음악목록 (RecMusiclistsResDto). 만약 결과가 없다면 null 반환
   */
  @Transactional(readOnly = true)
  public RecMusiclistsResDto getRecentRecMusiclists(String userId) {
    RecMusiclists recMusicList = recMusicListsJpaRepository
        .findFirstByUserIdOrderByCreatedAtDesc(Long.parseLong(userId));
    return recMusicList == null ? null : new RecMusiclistsResDto(recMusicList);
  }

  /**
   * 특정 사용자의 모든 추천 음악 목록을 조회합니다. (최근 20개)
   *
   * @param userId 사용자 ID (String)
   * @return 해당 사용자의 모든 추천 음악 목록. 결과가 없다면 빈 리스트 반환(RecMusiclistsResDto 리스트).
   */
  @Transactional(readOnly = true)
  public List<RecMusiclistsResDto> getAllRecMusiclists(String userId) {
    PageRequest pageRequest = PageRequest.of(0, 20);
    return recMusicListsJpaRepository.findByUserIdOrderByCreatedAtDesc(Long.parseLong(userId), pageRequest).stream()
        .map(RecMusiclistsResDto::new)
        .collect(Collectors.toList());
  }

}
