package com.blueme.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blueme.backend.dto.healthinfodto.HealthInfoSaveReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class IndexController {
	
	// 테스트용
	@GetMapping("/jwt-test")
	public String jwtTest() {
		return "jwtTest 요청 성공";
	}
	
	@PostMapping("/health/hearthrate")
    public Long test222(@RequestBody HealthInfoSaveReqDto request) {
        String userId = request.getUser_id();
        String heartRate = request.getHeartrate();
        String calorie = request.getCalorie();
        String speed = request.getSpeed();
        String step = request.getStep();
        
        System.out.println(userId);
        System.out.println(heartRate);

        // 여기에서 userId와 averageHeartRate를 사용하여 필요한 처리 수행...

        return 1L;  // 일련번호 반환. 실제로는 DB에 저장된 데이터의 ID 등이 될 수 있습니다.
    }
}
