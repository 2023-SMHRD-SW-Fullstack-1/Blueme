package com.blueme.backend.config;

import org.springframework.context.annotation.Configuration;

/**
 * ChatGptConfig는 ChatGPTAPI 통신을 위한 설정 클래스입니다.
 * <p>
 * 이 클래스에서는 ChatGPT 의 기본 설정을 관리합니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-14
 */
@Configuration
public class ChatGptConfig {
        public static final String AUTHORIZATION = "Authorization";
        public static final String BEARER = "Bearer ";
        public static final String CHAT_MODEL = "gpt-3.5-turbo-16k";
        public static final Integer MAX_TOKEN = 600;
        public static final Boolean STREAM = false;
        public static final String ROLE = "user";
        public static final Double TEMPERATURE = 0.8;
        // public static final Double TOP_P = 1.0;
        public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
        public static final String CHAT_URL = "https://api.openai.com/v1/chat/completions";
        public static final String QUESTION_TEMPLATE = "국가는 대한민국이고 %s에 현재온도는 섭씨%s이고 습도는 %s이고, 평균심박수는 %s bpm으로 %s 이고 속도는%skm/h이고 분당걸음수는 %s 으로 %s 이고 내가 좋아하는 장르는 %s, %s 이고 좋아하는 가수는 %s, %s 이고 날씨는 %s 인데, (id,title,artist,genre,tag) 형식으로 내가 노래리스트가 %s 형식으로 주어져있으며, 총 60곡이 있습니다. 제가 원하는것은 현재 날씨와 건강데이터와 상황데이터에서 건강데이터와 상황데이터를 참고하되 노래의 태그를 우선순위로 가장 잘 어울리는 상위 10곡의 추천리스트입니다. JSON 형식으로 '전체적인 추천리스트 제목' 은 title로 하되 여러번 질문하더라도 안겹치게 다채롭고 문학적으로, '전체적인추천리스트 제목을 추천한이유'는 reason 으로 하되 계절정보는 제외하고 이유도 문학적으로, 그리고 musicIds배열을 포함해주세요. 추천이유를 자세히 설명하되 60자이내로, 내가 요구하지 않은 추가정보 없이 깔끔하게 작성해주세요.";
        public static final String QUESTION_TEMPLATE2 = "국가는 대한민국이고 현재온도는 섭씨%s이고 습도는 %s이고, 평균심박수는 %s bpm으로 %s 이고 속도는%skm/h이고 분당걸음수는 %s 으로 %s 이고 날씨는 %s 인데, (id,title,artist,genre,tag) 형식으로 내가 노래리스트가 %s 형식으로 주어져있으며, 총 60곡이 있습니다. 제가 원하는것은 현재 건강데이터와 상황데이터에서 건강데이터와 상황데이터를 참고하되 노래의 태그를 우선순위로 가장 잘 어울리는 상위 10곡의 추천리스트입니다. JSON 형식으로 '전체적인 추천리스트 제목' 은 title로 하되 여러번 질문하더라도 안겹치게 다채롭고 문학적으로, '전체적인추천리스트 제목을 추천한이유'는 reason 으로 하되 계절정보는 제외하고 이유도 문학적으로, 그리고 musicIds배열을 포함해주세요. 추천이유를 자세히 설명하되 60자이내로, 내가 요구하지 않은 추가정보 없이 깔끔하게 작성해주세요.";
        public static final String QUESTION_TEMPLATE3 = "국가는 대한민국이고 현재온도는 섭씨%s이고 습도는 %s이고, 평균심박수는 %s bpm으로 %s 이고 속도는%skm/h이고 분당걸음수는 %s 으로 %s 내가 좋아하는 장르는 %s, %s 이고 좋아하는 가수는 %s, %s 이고 인데, (id,title,artist,genre,tag) 형식으로 내가 노래리스트가 %s 형식으로 주어져있으며, 총 60곡이 있습니다. 제가 원하는것은 현재 건강데이터와 상황데이터에서 건강데이터와 상황데이터를 참고하되 노래의 태그를 우선순위로 가장 잘 어울리는 상위 10곡의 추천리스트입니다. JSON 형식으로 '전체적인 추천리스트 제목' 은 title로 하되 여러번 질문하더라도 안겹치게 다채롭고 문학적으로, '전체적인추천리스트 제목을 추천한이유'는 reason 으로 하되 계절정보는 제외하고 이유도 문학적으로, 그리고 musicIds배열을 포함해주세요. 추천이유를 자세히 설명하되 60자이내로, 내가 요구하지 않은 추가정보 없이 깔끔하게 작성해주세요.";
        public static final String QUESTION_TEMPLATE4 = "국가는 대한민국이고 현재 시각은 %s이고 평균심박수는 %s bpm으로 %s 이고 속도는%skm/h이고 분당걸음수는 %s 으로 %s 인데, (id,title,artist,genre,tag) 형식으로 내가 노래리스트가 %s 형식으로 주어져있으며, 총 60곡이 있습니다. 제가 원하는것은 현재 건강데이터와 상황데이터에서 건강데이터와 상황데이터를 참고하되 계절은 제외한 노래의 태그를 우선순위로 가장 잘 어울리는 상위 10곡의 추천리스트입니다. JSON 형식으로 '전체적인 추천리스트 제목' 은 title로 하되 여러번 질문하더라도 안겹치게 다채롭고 문학적으로 하되 계절에 관한 정보는 제외해주고, '전체적인추천리스트 제목을 추천한이유'는 reason 으로 하되 계절정보에 관한 이유는 제외하고 문학적으로, 그리고 musicIds배열을 포함해주세요. 추천이유를 자세히 설명하되 문학적으로 최대 60자이내로, 내가 요구하지 않은 추가정보 없이 깔끔하게 작성해주세요.";
        public static final String QUESTION_TEMPLATE5 = "국가는 대한민국이고 평균심박수는 %s bpm으로 %s 이고 속도는%skm/h이고 분당걸음수는 %s 으로 %s 인데, (id,title,artist,genre,tag) 형식으로 내가 노래리스트가 %s 형식으로 주어져있으며, 총 60곡이 있습니다. 제가 원하는것은 현재 건강데이터와 상황데이터에서 건강데이터와 상황데이터를 참고하되 계절을 제외한 노래의 태그를 우선순위로 가장 잘 어울리는 상위 10곡의 추천리스트입니다. JSON 형식으로 '전체적인 추천리스트 제목' 은 title로 하되 여러번 질문하더라도 안겹치게 다채롭고 문학적으로, '전체적인추천리스트 제목을 추천한이유'는 reason 으로 하되 계절정보는 제외하고 이유도 문학적으로, 그리고 musicIds배열을 포함해주세요. 추천이유를 자세히 설명하되 60자이내로, 내가 요구하지 않은 추가정보 없이 깔끔하게 작성해주세요.";

}