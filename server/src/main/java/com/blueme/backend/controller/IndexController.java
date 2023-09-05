package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.HeartRatesReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class IndexController {
	
	// 테스트용
	@GetMapping("/")
	public Long reservation() {
		return 1L;
	}
	
	@PostMapping("/health/hearthrate")
    public Long test222(@RequestBody HeartRatesReqDto request) {
        String userId = request.getUser_id();
        Double averageHeartRate = request.getHeart_rate();
        
        System.out.println(userId);
        System.out.println(averageHeartRate);

        // 여기에서 userId와 averageHeartRate를 사용하여 필요한 처리 수행...

        return 1L;  // 일련번호 반환. 실제로는 DB에 저장된 데이터의 ID 등이 될 수 있습니다.
    }
}
