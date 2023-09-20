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
import com.blueme.backend.enums.Season;
import com.blueme.backend.model.entity.HealthInfos;
import com.blueme.backend.model.vo.ChatGptMessage;
import com.blueme.backend.model.vo.WeatherSummary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ChatGptService는 ChatGPT 모델을 사용하여 질문에 답변을 생성하고 관련된 데이터를 처리하는 서비스입니다.
 * <p>
 * ChatGPT를 활용하여 답변을 받아옵니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-18
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ChatGptService {

        private final RestTemplate restTemplate;
        private final MusicsService musicsService;

        @Value("${api-key.chat-gpt}")
        private String apiKey;

        /**
         * ChatGptReqDto 객체로 HTTP 엔티티를 생성합니다.
         *
         * @param chatGptRequest ChatGptReqDto 객체
         * @return HTTP 엔티티
         */
        public HttpEntity<ChatGptReqDto> buildHttpEntity(ChatGptReqDto chatGptRequest) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
                httpHeaders.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + apiKey);
                return new HttpEntity<>(chatGptRequest, httpHeaders);
        }

        /**
         * ChatGPT에 질문을 보내고 답변을 받습니다.
         *
         * @param chatGptRequestHttpEntity ChatGptReqDto 객체로 생성된 HTTP엔티티
         * @return ChatGptResDto 객체 (답변)
         */
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

        /**
         * ChatGPT에 질문을 보내고 답변을 받아옵니다.
         * 
         * @param questionRequest QuestionReqDto 객체 (질문)
         * @return ChatGptResDto 객체 (답변)
         */
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

        /**
         * 현재 계절 상태
         *
         * @return 계절 상태 (String)
         */
        public String getSeasonStatus() {
                LocalDate now = LocalDate.now();
                Month month = now.getMonth();

                switch (month) {
                        case MARCH:
                        case APRIL:
                        case MAY:
                                return Season.SPRING.getTag();
                        case JUNE:
                        case JULY:
                        case AUGUST:
                                return Season.SUMMER.getTag();
                        case SEPTEMBER:
                        case OCTOBER:
                        case NOVEMBER:
                                return Season.FALL.getTag();
                        default:
                                return Season.WINTER.getTag();
                }
        }

        /**
         * 현재 시간 상태
         *
         * @return 시간 상태 (String)
         */
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

        /**
         * 심장 박동수 상태
         *
         * @param avgHeartRate 평균 심장 박동수
         * @return 심장 박동수 상태 (String)
         */
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

        /**
         * 일정한 기준에 따라 현재 상태를 반환합니다.
         *
         * @param avgSpeed       평균 속도
         * @param stepsPerMinute 분당 걸음수
         * @param avgHeartRate   평균 심장 박동수
         * @return 속도 상태 (String)
         */
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

        /**
         * 뮤직 리스트를 문자열로 반환합니다.
         *
         * @return 뮤직 리스트 (String)
         */
        public String getMusicList(WeatherSummary weatherSummary, HealthInfos healthInfo) {
                // 현재 전체 태그 상황
                // 헤어짐,심술,tired,rain,아침,sad,캠퍼스,운전,신남,college,afternoon,저녁,사업,viciously,spring,여행,sun,소중,오후,store,파티,밤,rest,슬픔,depressed,restaurant,summer,추위,집,햇살,크리스마스,lunch,불안,home,새벽
                // 가을,우울,subway,노래방,바람,travel,flutter,겨울,rage,gym,더위,unrest,drive,autumn,운동,봄,night,hot,눈보라,휴식,cold,오전,winter,비,지하철,snow,그리움,카페,party,여름,점심,공부,dawn,study,고백,morning,cloud,beach,wind,흐림,happy,evening,바다,설렘,tag,
                // 음악 가져오기 로직 수정중

                // 음악 가져오기
                List<ChatGptMusicsDto> musicsList = musicsService.getRandomEntities(100)
                                .stream().map(ChatGptMusicsDto::new).collect(Collectors.toList());
                String musicsString = musicsList.stream().map(ChatGptMusicsDto::toString)
                                .collect(Collectors.joining(""));
                return musicsString;
        }

        /**
         * 질의문을 작성합니다.
         *
         * @param weatherSummary 날씨 요약 정보
         * @param healthInfo     건강 정보
         * @return 생성된 질의문 (String)
         */
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
                String musicsString = getMusicList(weatherSummary, healthInfo);
                String heartRateData = getHeartRateStatus(Double.parseDouble(avgHeartRate));
                String speedData = getSpeedStatus(Double.parseDouble(avgSpeed),
                                Double.parseDouble(stepsPerMinute), Double.parseDouble(avgHeartRate));
                String timeData = getTimeStatus();
                String seasonData = getSeasonStatus();

                // 1~5까지 랜덤한 질의문 생성(GPT의 다채로운답변을위해)
                Random random = new Random();
                int randomNumber = random.nextInt(13) + 1;

                // 테스트
                // int randomNumber = 13;

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