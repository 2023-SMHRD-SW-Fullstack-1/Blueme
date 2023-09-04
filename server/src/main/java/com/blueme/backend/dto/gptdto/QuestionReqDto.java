package com.blueme.backend.dto.gptdto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//Front단에서 요청하는 DTO
public class QuestionReqDto {
	
    private String question;
    
}