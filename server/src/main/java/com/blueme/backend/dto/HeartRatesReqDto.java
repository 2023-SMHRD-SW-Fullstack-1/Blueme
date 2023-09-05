package com.blueme.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeartRatesReqDto {
	private String user_id;
    private Double heart_rate;
}
