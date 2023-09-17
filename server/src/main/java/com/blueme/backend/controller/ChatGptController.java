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
import lombok.extern.slf4j.Slf4j;

/**
 * ChatGptController는 ChatGPT와의 상호작용을 관리하는 컨트롤러입니다.
 * 이 클래스에서는 질문에 대한 응답을 처리하는 API 엔드포인트를 제공합니다.
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-07
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat-gpt")
@RestController
public class ChatGptController {

    private final APIResponse apiResponse;
    private final ChatGptService chatGptService;

    /**
     * 질문에 대한 응답을 처리하는 메소드입니다.
     *
     * @param locale          클라이언트의 로케일 정보입니다.
     * @param request         HTTP 요청 객체입니다.
     * @param response        HTTP 응답 객체입니다.
     * @param questionRequest 클라이언트로부터 받은 질문 정보를 담고 있는 DTO 객체입니다.
     *
     * @return GPT의 응답 내용이 담긴 ResponseEntity 객체를 반환합니다.
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

}