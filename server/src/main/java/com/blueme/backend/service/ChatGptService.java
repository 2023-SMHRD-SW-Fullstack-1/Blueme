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
import com.blueme.backend.enums.EmotionState;
import com.blueme.backend.enums.PlaceActivity;
import com.blueme.backend.enums.Season;
import com.blueme.backend.enums.TimeOfDay;
import com.blueme.backend.model.entity.HealthInfos;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.model.vo.ChatGptMessage;
import com.blueme.backend.model.vo.WeatherSummary;
import com.blueme.backend.model.vo.WeatherInfo.Weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ChatGptService는 ChatGPT 모델을 사용하여 질문에 답변을 생성하고 관련된 데이터를 처리하는 서비스입니다.
 * <p>
 * ChatGPT를 활용하여 답변을 받아옵니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.1
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
         * 현재 계절 상태 메서드입니다.
         *
         * @return 계절 상태 (String)
         */
        public String getSeasonStatus(String language) {
                LocalDate now = LocalDate.now();
                Month month = now.getMonth();

                String seasonTag;
                switch (month) {
                        case MARCH:
                        case APRIL:
                        case MAY:
                                seasonTag = language.equals("KOR") ? Season.SPRING.getTag()
                                                : Season.SPRING_ENG.getTag();
                                break;
                        case JUNE:
                        case JULY:
                        case AUGUST:
                                seasonTag = language.equals("KOR") ? Season.SUMMER.getTag()
                                                : Season.SPRING_ENG.getTag();
                                break;
                        case SEPTEMBER:
                        case OCTOBER:
                        case NOVEMBER:
                                seasonTag = language.equals("KOR") ? Season.FALL.getTag()
                                                : Season.FALL_ENG.getTag();
                                break;
                        default:
                                seasonTag = language.equals("KOR") ? Season.WINTER.getTag()
                                                : Season.WINTER_ENG.getTag();
                                break;
                }
                return seasonTag;
        }

        /**
         * 현재 시간 상태 메서드입니다.
         *
         * @return 시간 상태 (String)
         */
        public String getTimeStatus(String language) {
                LocalTime now = LocalTime.now();
                int hour = now.getHour();

                String timeTag;
                if (hour >= 0 && hour < 6) {
                        timeTag = language.equals("KOR") ? TimeOfDay.DAWN.getTag()
                                        : TimeOfDay.DAWN_ENG.getTag();
                } else if (hour >= 6 && hour < 12) {
                        timeTag = language.equals("KOR") ? TimeOfDay.MORNING.getTag()
                                        : TimeOfDay.MORNING_ENG.getTag();
                } else if (hour >= 12 && hour < 13) {
                        timeTag = language.equals("KOR") ? TimeOfDay.LUNCH.getTag()
                                        : TimeOfDay.LUNCH_ENG.getTag();
                } else if (hour >= 13 && hour < 18) {
                        timeTag = language.equals("KOR") ? TimeOfDay.AFTERNOON.getTag()
                                        : TimeOfDay.AFTERNOON_ENG.getTag();
                } else if (hour >= 18 && hour < 21) {
                        timeTag = language.equals("KOR") ? TimeOfDay.EVENING.getTag()
                                        : TimeOfDay.EVENING_ENG.getTag();
                } else {
                        timeTag = language.equals("KOR") ? TimeOfDay.NIGHT.getTag()
                                        : TimeOfDay.NIGHT_ENG.getTag();
                }
                return timeTag;
        }

        public String getWeatherStatus(String language, WeatherSummary weatherSummary) {
                return "";
        }

        /**
         * 날씨,상태 기반 감정 상태 추측하는 로직 입니다.
         * 
         * <p>
         * 참고문헌
         * </p>
         * <p>
         * Weather impacts expressed sentiment (Baylis et al., 2018)
         * </p>
         * <p>
         * "The temperature of emotions(Francisco Barbosa Escobar., 2021)
         * </p>
         * 
         * 
         * @param language       언어(KOR, ENG)
         * @param weatherSummary 현재 날씨상태
         * @param healthInfo     상태정보
         * @return 감정 (String)
         */
        public String getEmotionStatus(String language, WeatherSummary weatherSummary, HealthInfos healthInfo) {
                // condition : ThunderStorm , Drizzle(보슬보슬비가 붓다), Rain,
                // Snow, Atmosphere(안개), Clear, Clouds
                String condition = weatherSummary.getCondition();
                String humidity = weatherSummary.getHumidity();
                String temp = weatherSummary.getTemp();

                double temperature = Double.parseDouble(temp);
                double humid = Double.parseDouble(humidity);

                // 심장박동수 상수
                final double VERY_LOW_THRESHOLD = 60;
                final double LOW_THRESHOLD = 80;
                final double NORMAL_THRESHOLD = 110;
                final double HIGH_THRESHOLD = 130;

                // 운동 상수
                final double STATIONARY_SPEED = 1.5;
                final double WALKING_RUNNING_SPEED = 5.0;
                final double RUNNING_BICYCLE_SPEED = 15.0;
                final double CAR_SPEED = 25;
                final double AIRPLANE_SPEED = 180;

                // 워치 데이터
                double speed = Double.parseDouble(healthInfo.getSpeed());
                double heartRate = Double.parseDouble(healthInfo.getHeartrate());
                int stepsPerMinute = Integer.parseInt(healthInfo.getStep());

                final int INACTIVE_STEPS_THRESHOLD = 20;
                final int NORMAL_ACTIVE_STEPS_THRESHOLD = 40;

                String emotionstate;
                switch (condition) {
                        case "ThunderStorm":
                                if (heartRate > HIGH_THRESHOLD) {
                                        emotionstate = EmotionState.ANXIOUS.getTag();
                                } else {
                                        emotionstate = EmotionState.UNREST.getTag();
                                }
                                break;

                        case "Drizzle":
                        case "Rain":
                                if (temperature < 20) {
                                        emotionstate = (heartRate > NORMAL_THRESHOLD ? EmotionState.SAD_ENG.getTag()
                                                        : EmotionState.GLOOMY.getTag());
                                } else {
                                        emotionstate = (heartRate > NORMAL_THRESHOLD ? EmotionState.TIRED.getTag()
                                                        : EmotionState.MISSING.getTag());
                                }
                                break;

                        case "Snow":
                                if (speed <= STATIONARY_SPEED) {
                                        emotionstate = (heartRate < LOW_THRESHOLD ? EmotionState.SAD.getTag()
                                                        : EmotionState.VALUABLE.getTag());
                                } else {
                                        emotionstate = (heartRate < LOW_THRESHOLD ? EmotionState.MISSING.getTag()
                                                        : EmotionState.BEAT.getTag());
                                }
                                break;

                        case "Atmosphere":
                                if (speed > RUNNING_BICYCLE_SPEED && speed <= CAR_SPEED) {
                                        emotionstate = (heartRate > HIGH_THRESHOLD ? EmotionState.UNREST.getTag()
                                                        : EmotionState.GLOOMY.getTag());
                                } else {
                                        emotionstate = (heartRate > HIGH_THRESHOLD ? EmotionState.RAGE.getTag()
                                                        : EmotionState.GLOOMY.getTag());
                                }
                                break;

                        case "Clear":
                                if (temperature > 30) {
                                        emotionstate = (stepsPerMinute >= NORMAL_ACTIVE_STEPS_THRESHOLD
                                                        ? EmotionState.TIRED.getTag()
                                                        :

                                                        EmotionState.HAPPY.getTag());
                                } else if (temperature <= 30 && temperature >= 20) {
                                        emotionstate = (stepsPerMinute >= NORMAL_ACTIVE_STEPS_THRESHOLD ?

                                                        EmotionState.FLUTTER.getTag() :

                                                        EmotionState.BEAT.getTag());
                                } else {
                                        emotionstate = (stepsPerMinute >= NORMAL_ACTIVE_STEPS_THRESHOLD ?

                                                        EmotionState.BEAT.getTag() :

                                                        EmotionState.FLUTTER.getTag());
                                }
                                break;

                        case "Clouds":
                                if (humid > 80) {
                                        emotionstate = EmotionState.DEPRESSED.getTag();
                                } else {
                                        emotionstate = EmotionState.UNREST.getTag();
                                }
                                break;

                        default:

                                emotionstate = null;

                }

                return emotionstate;

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
         * 건강 정보 기반 장소 추측하는 메서드입니다.
         * 
         * @param healthInfo 건강정보(HealthInfos)
         * @return 장소 (String)
         */
        public String getLocationState(HealthInfos healthInfo) {
                // 상수 선언
                final double STATIONARY_SPEED = 1.5;
                final double WALKING_SPEED = 5.0;
                final double RUNNING_SPEED = 10.0;
                final double BICYCLE_SPEED = 15.0;
                final double CAR_SPEED = 25;

                // 심장박동수 상태
                final double VERY_LOW_THRESHOLD = 60;
                final double LOW_THRESHOLD = 80;
                final double NORMAL_THRESHOLD = 110;

                Double speedVal = Double.parseDouble(healthInfo.getSpeed());
                Double heartRateVal = Double.parseDouble(healthInfo.getHeartrate());

                String placeActivityTag = "";

                if (speedVal <= STATIONARY_SPEED) {
                        if (heartRateVal <= VERY_LOW_THRESHOLD) {
                                placeActivityTag = PlaceActivity.HOME.getTag();
                        } else if (heartRateVal <= LOW_THRESHOLD) {
                                placeActivityTag = PlaceActivity.STUDY.getTag();
                        } else {
                                placeActivityTag = PlaceActivity.GYM.getTag();
                        }
                } else if (speedVal <= WALKING_SPEED) {
                        if (heartRateVal <= NORMAL_THRESHOLD) {
                                placeActivityTag = PlaceActivity.CAMPUS.getTag();
                        } else {
                                placeActivityTag = PlaceActivity.PARTY.getTag();
                        }
                } else if (speedVal <= RUNNING_SPEED) {
                        if (heartRateVal <= NORMAL_THRESHOLD) {
                                placeActivityTag = PlaceActivity.BEACH.getTag();
                        } else {
                                placeActivityTag = PlaceActivity.CAMPUS.getTag();
                        }
                } else if (speedVal <= BICYCLE_SPEED) {
                        if (heartRateVal < NORMAL_THRESHOLD) {
                                placeActivityTag = PlaceActivity.CAMPUS.getTag();
                        } else {
                                placeActivityTag = PlaceActivity.GYM_ENG.getTag();
                        }
                }

                else {
                        if (heartRateVal < LOW_THRESHOLD) {
                                placeActivityTag = PlaceActivity.DRIVING.getTag();
                        } else {
                                placeActivityTag = PlaceActivity.SUBWAY_ENG.getTag();
                        }
                }

                return placeActivityTag;
        }

        /**
         * 뮤직 리스트를 문자열로 반환합니다.
         *
         * @return 뮤직 리스트 (String)
         */
        public String getMusicList(WeatherSummary weatherSummary, HealthInfos healthInfo) {
                // 음악 가져오기 로직 수정중 (현재 곡 수 : 190 곡, 190곡중 약 60곡 뽑기)
                List<Musics> musics = new ArrayList<Musics>();
                // 계절 태그로 계절 분류 (10개 or DB에 적을경우 더 적은 개수)
                String seasonData = getSeasonStatus("KOR");
                String seasonDataEng = getSeasonStatus("ENG");
                musics.addAll(musicsService.getMusicsWithTag(seasonData, 5));
                musics.addAll(musicsService.getMusicsWithTag(seasonDataEng, 5));

                // 시간 태그로 시간 분류 (10개 or DB에 적을경우 더 적은 개수)
                String timeData = getTimeStatus("KOR");
                String timeDataEng = getTimeStatus("ENG");
                musics.addAll(musicsService.getMusicsWithTag(timeData, 5));
                musics.addAll(musicsService.getMusicsWithTag(timeDataEng, 5));

                // 감정 태그로 감정 분류 (25개 감정기반 비중높음, or DB에 적을경우 더 적은 개수)
                String emotionData = getEmotionStatus("KOR", weatherSummary, healthInfo);
                musics.addAll(musicsService.getMusicsWithTag(emotionData, 25));

                // 장소 태그로 장소 분류 (25개, or DB에 적을경우 더 적은 개수)

                // 음악 가져오기 (현재 랜덤 100개)
                List<ChatGptMusicsDto> musicsList = musicsService.getRandomEntities(60)
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
                String timeData = getTimeStatus("KOR");
                String seasonData = getSeasonStatus("KOR");

                // 1~5까지 랜덤한 질의문 생성(GPT의 다채로운답변을위해)
                Random random = new Random();
                // int randomNumber = random.nextInt(13) + 1;

                // 테스트
                int randomNumber = 1;

                String question = null;
                log.info("포맷타입 = {}", randomNumber);
                if (randomNumber == 1) {
                        question = String.format(ChatGptConfig.QUESTION_TEMPLATE + seasonData + " " + timeData,
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