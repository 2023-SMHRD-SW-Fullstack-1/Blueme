package com.blueme.backend.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.gptdto.ChatGptMusicsDto;
import com.blueme.backend.dto.gptdto.ChatGptResDto;
import com.blueme.backend.dto.gptdto.QuestionReqDto;
import com.blueme.backend.model.entity.Musics;
import com.blueme.backend.service.ChatGptService;
import com.blueme.backend.utils.APIResponse;
import com.blueme.backend.utils.ResponseCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
작성자: 김혁
날짜(수정포함): 2023-09-07
설명: ChatGpt 컨트롤러
*/

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat-gpt")
@RestController
public class ChatGptController {

    private final APIResponse apiResponse;
    private final ChatGptService chatGptService;

    /*
     * 질의
     */
    @PostMapping("/question")
    public ResponseEntity sendQuestion(
            Locale locale,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody QuestionReqDto questionRequest) {
        String code = ResponseCode.CD_SUCCESS;
        ChatGptResDto chatGptResponse = null;
        log.info("start gpt question");
        try {
            chatGptResponse = chatGptService.askQuestion(questionRequest);
        } catch (Exception e) {
            apiResponse.printErrorMessage(e);
            code = e.getMessage();
        }

        return apiResponse.getResponseEntity(locale, code,
                chatGptResponse != null ? chatGptResponse.getChoices().get(0).getMessage().getContent()
                        : new ChatGptResDto());
    }

    /*
     * 테스트용
     */
    // @GetMapping("/")
    // public String makeQuestion() {
    // return chatGptService.makeQuestion();
    // }
}