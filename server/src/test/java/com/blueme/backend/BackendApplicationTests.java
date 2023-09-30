package com.blueme.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * jUnit 테스트 프로파일 생성 추가
 * 
 * @author김혁
 * @version 1.0
 * @since 2023-09-30
 */
@SpringBootTest
@ActiveProfiles("jwt")
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
