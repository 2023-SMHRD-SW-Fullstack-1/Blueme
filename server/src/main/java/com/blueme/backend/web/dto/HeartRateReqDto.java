package com.blueme.backend.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeartRateReqDto {
	private String user_id;
    private Double heart_rate;
}
