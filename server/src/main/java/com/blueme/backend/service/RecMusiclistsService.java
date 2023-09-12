package com.blueme.backend.service;

import com.blueme.backend.api.client.WeatherAPIClient;
import com.blueme.backend.dto.gptdto.ChatGptResDto;
import com.blueme.backend.dto.gptdto.QuestionReqDto;
import com.blueme.backend.dto.musiclistsdto.RecMusicListSaveDto;
import com.blueme.backend.dto.recmusiclistsdto.RecMusiclistsResDto;
import com.blueme.backend.model.entity.HealthInfos;
import com.blueme.backend.model.entity.RecMusiclists;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.HealthInfosJpaRepository;
import com.blueme.backend.model.repository.RecMusicListsJpaRepository;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.model.vo.WeatherInfo;
import com.blueme.backend.model.vo.WeatherSummary;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Mono;

/*
작성자: 김혁
날짜(수정포함): 2023-09-12
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
   * post 추천 음악등록(chatGPT)
   */
  @Transactional
  public Long registerRecMusiclist(String userId) {
    // 건강데이터 있는지 확인, 있으면 가장 최신의 건강데이터 1개 추출
    Optional<HealthInfos> healthInfo = healthInfosJpaRepository.findFirstByUserIdOrderByCreatedAtDesc(
        Long.parseLong(userId));

    if (healthInfo.isPresent()) { // 건강데이터가 존재한다면
      // 날씨 데이터 가져오기
      WeatherInfo weatherData = weatherAPIClient.getWeather(healthInfo.get().getLat(), healthInfo.get().getLon());
      String condition = weatherData.getWeather().get(0).getMain();
      String temp = Double.toString(weatherData.getMain().getTemp());
      String humidity = Integer.toString(weatherData.getMain().getHumidity());
      WeatherSummary weatherSummary = new WeatherSummary(condition, temp, humidity);

      // chatGPT 에게 보낼 질의문 생성하기
      String question = chatGptService.makeQuestion(weatherSummary, healthInfo.get());
      QuestionReqDto questionDto = new QuestionReqDto(question);
      ChatGptResDto response = chatGptService.askQuestion(questionDto);

      // ChatGptResDto 에서 사용할 데이터 추출
      String content = response.getChoices().get(0).getMessage().getContent();
      ObjectMapper objectMapper = new ObjectMapper();
      RecMusicListSaveDto recMusiclistSaveDto;

      Long rec_musiclist_id = -1L;
      try {
        recMusiclistSaveDto = objectMapper.readValue(content, RecMusicListSaveDto.class);
        recMusiclistSaveDto.setUserId(userId);
        rec_musiclist_id = musicListsService.save(recMusiclistSaveDto);
      } catch (JsonProcessingException e) {
        log.error("jackson 저장 실패!", e);
        return -1L;
      }
      return rec_musiclist_id;
    } else {
      return -1L;
    }
  }

  /*
   * 
   */

  /*
   * get 모든 추천리스트 조회
   */
  @Transactional
  public List<RecMusiclistsResDto> getAllRecMusiclists(String userId) {
    try {
      List<RecMusiclists> recMusiclistsList = recMusicListsJpaRepository.findByUserId(Long.parseLong(userId));
      if (recMusiclistsList.size() >= 1) {
        // 추천음악이 있으면
        List<RecMusiclistsResDto> result = new ArrayList<>();
        for (RecMusiclists recMusiclist : recMusiclistsList) {
          Path filePath = Paths.get("\\usr\\blueme\\jackets\\"
              + recMusiclist.getRecMusicListDetail().get(0).getMusic().getJacketFilePath() + ".jpg");
          File file = filePath.toFile();
          if (!file.exists()) {
            log.debug("재킷파일이 존재하지 않습니다 경로 = {}", file.getAbsolutePath());
          }
          ImageConverter<File, String> converter = new ImageToBase64();
          String base64 = null;
          base64 = converter.convert(file);
          if (base64 == null) {
            log.debug("재킷파일을 base64로 변환할 수 없습니다");
          }
          RecMusiclistsResDto recMusiclistsResDto = new RecMusiclistsResDto(recMusiclist, base64);
          result.add(recMusiclistsResDto);
        }
        return result;
      } else {
        return null;
      }

    } catch (Exception e) {
      throw new RuntimeException("추천음악이미지파일 전송 실패", e);
    }
  }
}
