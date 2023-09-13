package com.blueme.backend.config;

import org.springframework.context.annotation.Configuration;

/*
작성자: 김혁
날짜(수정포함): 2023-09-07
설명: ChatGpt 설정파일
*/

@Configuration
public class ChatGptConfig {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String CHAT_MODEL = "gpt-3.5-turbo";
    // 답변 최대 토큰수
    public static final Integer MAX_TOKEN = 500;
    public static final Boolean STREAM = false;
    public static final String ROLE = "user";
    public static final Double TEMPERATURE = 0.8;
    // public static final Double TOP_P = 1.0;
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    // completions : 질답
    public static final String CHAT_URL = "https://api.openai.com/v1/chat/completions";
    // 질의문 양식
    public static final String QUESTION_TEMPLATE = "현재 날씨가 %s 이고 현재온도는 섭씨%s이고 습도는 %s이고, 평균심박수는 %s bpm으로 %s 이고 속도는 %s km/h이고 분당걸음수는 %s이고 %s 인데, (id,title,artist,genre) 형식으로 내가 노래리스트가 %s 형식으로 주어져있으며, 총 100곡이 있습니다. 제가 원하는것은 현재 날씨와 건강데이터와 상황데이터에서 건강데이터와 상황데이터를 중점적으로 가장 잘 어울리는 상위 20곡의 추천리스트입니다. JSON 형식으로 '전체적인 추천리스트 제목' 은 title로 하되 제목은 우리 앱에 바로 표현될 정도로 문학적으로, '전체적인추천리스트 제목을 추천한이유'는 reason 으로 하되 이유도 문학적으로, 그리고 musicIds배열을 포함해주세요. 추천이유를 자세히 설명하되 내가 요구하지 않은 추가정보 없이 깔끔하게 작성해주세요.";

}