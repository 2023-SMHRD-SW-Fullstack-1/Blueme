package com.blueme.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blueme.backend.config.ChatGptConfig;
import com.blueme.backend.dto.gptdto.ChatGptReqDto;
import com.blueme.backend.dto.gptdto.ChatGptResDto;
import com.blueme.backend.dto.gptdto.QuestionReqDto;
import com.blueme.backend.model.vo.ChatGptMessage;

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

    @Value("${api-key.chat-gpt}")
    private String apiKey;

    public HttpEntity<ChatGptReqDto> buildHttpEntity(ChatGptReqDto chatGptRequest){
        HttpHeaders httpHeaders = new HttpHeaders();
        // json 으로 설정
        httpHeaders.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        // 요청헤더 추가
        httpHeaders.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + apiKey);
        return new HttpEntity<>(chatGptRequest, httpHeaders);
    }

    public ChatGptResDto getResponse(HttpEntity<ChatGptReqDto> chatGptRequestHttpEntity){

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        //답변이 길어질 경우 TimeOut Error가 1분 설정
        requestFactory.setReadTimeout(60 * 1000);   //  1min = 60 sec * 1,000ms
        restTemplate.setRequestFactory(requestFactory);

        ResponseEntity<ChatGptResDto> responseEntity = restTemplate.postForEntity(
          ChatGptConfig.CHAT_URL,
          chatGptRequestHttpEntity,
          ChatGptResDto.class);

        return responseEntity.getBody();
    }
    public ChatGptResDto askQuestion(QuestionReqDto questionRequest){
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
                                //ChatGptConfig.TOP_P
                        )
                )
        );
    }

    
}