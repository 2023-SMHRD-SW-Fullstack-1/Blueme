package com.blueme.backend.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.gptdto.ChatGptResDto;
import com.blueme.backend.dto.gptdto.QuestionReqDto;
import com.blueme.backend.service.ChatGptService;
import com.blueme.backend.utils.APIResponse;
import com.blueme.backend.utils.ResponseCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/chat-gpt")
@RestController
public class ChatGptController {
    private final APIResponse apiResponse;
    private final ChatGptService chatGptService;

    @PostMapping("/question")
    public ResponseEntity sendQuestion(
            Locale locale,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody QuestionReqDto questionRequest) {
        String code = ResponseCode.CD_SUCCESS;
        ChatGptResDto chatGptResponse = null;
        try {
            chatGptResponse = chatGptService.askQuestion(questionRequest);
        } catch (Exception e) {
            apiResponse.printErrorMessage(e);
            code = e.getMessage();
        }
        //return 부분은 자유롭게 수정하시면됩니다. ex)return chatGptResponse;
        return apiResponse.getResponseEntity(locale, code,
                chatGptResponse != null ? chatGptResponse.getChoices().get(0).getMessage().getContent() : new ChatGptResDto());
    }
}