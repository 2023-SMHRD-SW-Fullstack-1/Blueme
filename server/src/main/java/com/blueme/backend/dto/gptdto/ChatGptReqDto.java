package com.blueme.backend.dto.gptdto;

import java.util.List;

import com.blueme.backend.model.vo.ChatGptMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//chatGPT에 요청할 DTO Format
public class ChatGptReqDto {
    private String model;
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    private Double temperature;
    private Boolean stream;
    private List<ChatGptMessage> messages;

    //@JsonProperty("top_p")
    //private Double topP;

    @Builder
    public ChatGptReqDto(String model, Integer maxTokens, Double temperature,
                          Boolean stream, List<ChatGptMessage> messages
                          /*,Double topP*/) {
        this.model = model;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.stream = stream;
        this.messages = messages;
        //this.topP = topP;
    }
}