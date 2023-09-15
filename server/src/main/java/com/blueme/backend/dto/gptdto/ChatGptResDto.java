package com.blueme.backend.dto.gptdto;

import java.util.List;

import com.blueme.backend.model.vo.ChatGptMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 작성자 : 김혁
 * 작성일 : 2023-09-09
 * 설명   : ChatGptResDto
 */

@Getter
@NoArgsConstructor
public class ChatGptResDto {
    private String id;
    private String object;
    private long created;
    private String model;
    private Usage usage;
    private List<Choice> choices;

    @Getter
    @Setter
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;
        @JsonProperty("completion_tokens")
        private int completionTokens;
        @JsonProperty("total_tokens")
        private int totalTokens;
    }

    @Getter
    @Setter
    public static class Choice {
        private ChatGptMessage message;
        @JsonProperty("finish_reason")
        private String finishReason;
        private int index;
    }
}
