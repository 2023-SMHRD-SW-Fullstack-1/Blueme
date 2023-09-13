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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.blueme.backend.config.ChatGptConfig;
import com.blueme.backend.dto.gptdto.ChatGptMusicsDto;
import com.blueme.backend.dto.gptdto.ChatGptReqDto;
import com.blueme.backend.dto.gptdto.ChatGptResDto;
import com.blueme.backend.dto.gptdto.QuestionReqDto;
import com.blueme.backend.model.entity.HealthInfos;
import com.blueme.backend.model.vo.ChatGptMessage;
import com.blueme.backend.model.vo.WeatherSummary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-12
설명: ChatGpt 서비스
*/

@RequiredArgsConstructor
@Service
public class ChatGptService {

        private final RestTemplate restTemplate;
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

        // ChatGPT 에 질문하기
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

        // 심장박동수 상태
        public String getHeartRateStatus(double avgHeartRate) {
                final double VERY_LOW_THRESHOLD = 60;
                final double LOW_THRESHOLD = 80;
                final double NORMAL_THRESHOLD = 110;
                final double HIGH_THRESHOLD = 130;
                if (avgHeartRate <= VERY_LOW_THRESHOLD)
                        return "매우 낮은 상태";
                if (avgHeartRate <= LOW_THRESHOLD)
                        return "낮은 상태";
                if (avgHeartRate <= NORMAL_THRESHOLD)
                        return "안정된 상태";
                if (avgHeartRate <= HIGH_THRESHOLD)
                        return "높은 상태";

                return "매우 높은 상태";
        }

        // 속도 상태
        public String getSpeedStatus(double avgSpeed, double stepsPerMinute, double avgHeartRate) {
                final double STATIONARY_SPEED = 1.5;
                final double WALKING_RUNNING_SPEED = 5.0;
                final double RUNNING_BICYCLE_SPEED = 15.0;
                final double CAR_SPEED = 25;
                final double AIRPLANE_SPEED = 180;

                if (avgSpeed <= STATIONARY_SPEED) {
                        return "가만히 있는 중";
                } else if (avgSpeed <= WALKING_RUNNING_SPEED && stepsPerMinute >= 50) {
                        return "걷는 중";
                } else if (avgSpeed <= RUNNING_BICYCLE_SPEED) {
                        if (avgHeartRate >= 100 && avgHeartRate < 150) {
                                return "뛰는 중";
                        } else if (stepsPerMinute >= 50 || avgHeartRate >= 100) {
                                return "운동중";
                        } else {
                                return "자전거타는중 이거나 운전중이거나 차에 타있는중";
                        }
                } else if (avgSpeed <= CAR_SPEED) {
                        return "차량 이동중";
                } else {
                        return "비행기 타는 중 혹은 기차 타는 중";
                }

        }

        // 뮤직리스트 질의하기 적합한 문자열로 변환
        public String getMusicList() {
                List<ChatGptMusicsDto> musicsList = musicsService.getRandomEntities(100)
                                .stream().map(ChatGptMusicsDto::new).collect(Collectors.toList());
                String musicsString = musicsList.stream().map(ChatGptMusicsDto::toString)
                                .collect(Collectors.joining(""));
                return musicsString;
        }

        // 질의문 만들기
        public String makeQuestion(WeatherSummary weatherSummary, HealthInfos healthInfo) {
                // 날씨 데이터
                String condition = weatherSummary.getCondition();
                String temperature = String.valueOf(Double.parseDouble(weatherSummary.getTemp()) - 273) + "도";
                String humidity = weatherSummary.getHumidity() + "%";

                // 건강 데이터
                String avgHeartRate = healthInfo.getHeartrate();
                String avgSpeed = healthInfo.getSpeed();
                // String calorie = healthInfo.getCalorie();
                String stepsPerMinute = healthInfo.getStep();
                String musicsString = getMusicList();
                String heartRateData = getHeartRateStatus(Double.parseDouble(avgHeartRate));
                String speedData = getSpeedStatus(Double.parseDouble(avgSpeed),
                                Double.parseDouble(stepsPerMinute), Double.parseDouble(avgHeartRate));
                String question = String.format(ChatGptConfig.QUESTION_TEMPLATE, condition,
                                temperature, humidity,
                                avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData,
                                musicsString);
                return question;
        }

}