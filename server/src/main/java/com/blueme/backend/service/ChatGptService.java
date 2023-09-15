package com.blueme.backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

@Slf4j
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

        // 현재 계절 상태
        public String getSeasonStatus() {
                LocalDate now = LocalDate.now();
                Month month = now.getMonth();

                switch (month) {
                        case MARCH:
                        case APRIL:
                        case MAY:
                                return "봄";
                        case JUNE:
                        case JULY:
                        case AUGUST:
                                return "여름";
                        case SEPTEMBER:
                        case OCTOBER:
                        case NOVEMBER:
                                return "가을";
                        default:
                                return "겨울";
                }
        }

        // 현재 시간 상태
        public String getTimeStatus() {
                LocalTime now = LocalTime.now();
                int hour = now.getHour();

                if (hour >= 0 && hour < 5) {
                        return "새벽";
                } else if (hour >= 9 && hour < 12) {
                        return "아침";
                } else if (hour >= 12 && hour < 13) {
                        return "점심시간";
                } else if (hour >= 13 && hour < 18) {
                        return "오후";
                } else if (hour >= 18 && hour < 19) {
                        return "저녁밥시간";
                } else if (hour >= 19 && hour < 22) {
                        return "저녁";
                } else {
                        return "밤";
                }
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
                String timeData = getTimeStatus();
                String seasonData = getSeasonStatus();

                // 1~5까지 랜덤한 질의문 생성(GPT의 다채로운답변을위해)
                Random random = new Random();
                // int randomNumber = random.nextInt(13) + 1;

                // 테스트
                int randomNumber = 13;

                String question = null;
                log.info("포맷타입 = {}", randomNumber);
                if (randomNumber == 1) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE, seasonData + " " + timeData,
                                        temperature, humidity,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData, condition,
                                        musicsString);
                } else if (randomNumber == 2) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE2, seasonData + " " + timeData,
                                        temperature, humidity,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData,
                                        musicsString);
                } else if (randomNumber == 3) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE3,
                                        temperature, humidity,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData, condition,
                                        musicsString);
                } else if (randomNumber == 4) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE4,
                                        temperature,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData,
                                        musicsString);
                } else if (randomNumber == 5) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE5,
                                        temperature,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData,
                                        musicsString);
                } else if (randomNumber == 6) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE6, seasonData + " " + timeData,
                                        temperature, humidity,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData, condition,
                                        musicsString);
                } else if (randomNumber == 7) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE7, seasonData + " " + timeData,
                                        temperature, humidity,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData, condition,
                                        musicsString);
                } else if (randomNumber == 8) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE8, seasonData + " " + timeData,
                                        temperature, humidity,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData, condition,
                                        musicsString);
                } else if (randomNumber == 9) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE9, seasonData + " " + timeData,
                                        temperature, humidity,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData, condition,
                                        musicsString);
                } else if (randomNumber == 10) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE10, seasonData + " " + timeData,
                                        temperature, humidity,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData, condition,
                                        musicsString);
                } else if (randomNumber == 11) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE11, seasonData + " " + timeData,
                                        temperature, humidity,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData, condition,
                                        musicsString);
                } else if (randomNumber == 12) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE12, seasonData + " " + timeData,
                                        temperature, humidity,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData, condition,
                                        musicsString);
                } else if (randomNumber == 13) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE13, seasonData + " " + timeData,
                                        temperature, humidity,
                                        avgHeartRate, heartRateData, avgSpeed, stepsPerMinute, speedData, condition,
                                        musicsString);
                }

                log.info(question);
                return question;
        }

}