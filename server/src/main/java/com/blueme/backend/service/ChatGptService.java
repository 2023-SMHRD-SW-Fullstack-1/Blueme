package com.blueme.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blueme.backend.config.ChatGptConfig;
import com.blueme.backend.dto.gptdto.ChatGptMusicsDto;
import com.blueme.backend.dto.gptdto.ChatGptReqDto;
import com.blueme.backend.dto.gptdto.ChatGptResDto;
import com.blueme.backend.dto.gptdto.QuestionReqDto;
import com.blueme.backend.model.entity.HealthInfos;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.repository.MusicsJpaRepository;
import com.blueme.backend.model.vo.ChatGptMessage;
import com.blueme.backend.model.vo.WeatherSummary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-04
설명: ChatGpt 서비스
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatGptService {

        private final RestTemplate restTemplate;
        private final MusicsJpaRepository musicsJpaRepository;
        private final MusicsService musicsService;

        @Value("${api-key.chat-gpt}")
        private String apiKey;

        public HttpEntity<ChatGptReqDto> buildHttpEntity(ChatGptReqDto chatGptRequest) {
                HttpHeaders httpHeaders = new HttpHeaders();
                // json 으로 설정
                httpHeaders.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
                // 요청헤더 추가
                httpHeaders.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + apiKey);
                return new HttpEntity<>(chatGptRequest, httpHeaders);
        }

        public ChatGptResDto getResponse(HttpEntity<ChatGptReqDto> chatGptRequestHttpEntity) {

                SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
                requestFactory.setConnectTimeout(60000);
                // 답변이 길어질 경우 TimeOut Error가 1분 설정
                requestFactory.setReadTimeout(60 * 1000); // 1min = 60 sec * 1,000ms
                restTemplate.setRequestFactory(requestFactory);

                ResponseEntity<ChatGptResDto> responseEntity = restTemplate.postForEntity(
                                ChatGptConfig.CHAT_URL,
                                chatGptRequestHttpEntity,
                                ChatGptResDto.class);

                return responseEntity.getBody();
        }

        public ChatGptResDto askQuestion(QuestionReqDto questionRequest) {
                List<ChatGptMessage> messages = new ArrayList<>();
                messages.add(ChatGptMessage.builder()
                                .role(ChatGptConfig.ROLE)
                                .content(questionRequest.getQuestion())
                                .build());
                return this.getResponse(
                                this.buildHttpEntity(
                                                new ChatGptReqDto(
                                                                ChatGptConfig.CHAT_MODEL,
                                                                ChatGptConfig.MAX_TOKEN,
                                                                ChatGptConfig.TEMPERATURE,
                                                                ChatGptConfig.STREAM,
                                                                messages
                                                // ChatGptConfig.TOP_P
                                                )));
        }

        /*
         * ChatGPT 질의문 만들기
         */
        public String makeQuestion(WeatherSummary weatherSummary, HealthInfos healthInfo) {
                List<ChatGptMusicsDto> musicsList = musicsService.getRandomEntities(100)
                                .stream().map(ChatGptMusicsDto::new).collect(Collectors.toList());
                // 질의문에 추가할 음악데이터 문자
                String musicsString = musicsList.stream().map(ChatGptMusicsDto::toString)
                                .collect(Collectors.joining(""));
                // 날씨 데이터
                String condition = weatherSummary.getCondition();
                String temperature = String.valueOf(Double.parseDouble(weatherSummary.getTemp()) - 273) + "도";
                String humidity = weatherSummary.getHumidity() + "%";

                // 건강 데이터
                String avgHeartRate = healthInfo.getHeartrate();
                String avgSpeed = healthInfo.getSpeed();
                String calorie = healthInfo.getCalorie();
                String stepsPerMinute = healthInfo.getStep();

                // 건강 데이터 처리
                String heartRateData = "";
                String speedData = "";

                if (Double.parseDouble(avgHeartRate) <= 60) {
                        heartRateData = "매우 낮은 상태";
                } else if (Double.parseDouble(avgHeartRate) <= 80) {
                        heartRateData = "낮은 상태";
                } else if (Double.parseDouble(avgHeartRate) <= 110) {
                        heartRateData = "안정된 상태";
                } else if (Double.parseDouble(avgHeartRate) <= 130) {
                        heartRateData = "높은 상태";
                } else {
                        heartRateData = "매우 높은 상태";
                }

                if (Double.parseDouble(avgSpeed) <= 1.5) {
                        speedData = "가만히 있는 중";
                } else if (Double.parseDouble(avgSpeed) <= 5 && Double.parseDouble(stepsPerMinute) >= 50) {
                        speedData = "걷는 중";
                } else if ((Double.parseDouble(avgSpeed) > 5 && Double.parseDouble(avgSpeed) <= 10)
                                || (Double.parseDouble(avgHeartRate) >= 100
                                                && Double.parseDouble(avgHeartRate) < 150)) {
                        speedData = "뛰는 중";
                } else if (Double.parseDouble(avgSpeed) > 10 && Double.parseDouble(avgSpeed) <= 25) {
                        // 분당 걸음수와 심박수로 추가 판단
                        if (Double.parseDouble(stepsPerMinute) >= 50 || Double.parseDouble(avgHeartRate) >= 100) {
                                speedData = "운동중";
                        } else {
                                speedData = "자전거타는중 이거나 운전중이거나 차에 타있는중";
                        }
                } else if (Double.parseDouble(avgSpeed) > 25 && Double.parseDouble(avgSpeed) <= 90) {
                        speedData = "차량 이동중";
                } else {
                        speedData = "비행기 타는 중 혹은 기차 타는 중";
                }

                // 질의문 생성
                String question = String.format(ChatGptConfig.QUESTION_TEMPLATE, condition, temperature, humidity,
                                avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData, musicsString);
                return question;
        }

}