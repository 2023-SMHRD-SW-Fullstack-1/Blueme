package com.blueme.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestConfig는 REST 통신을 위한 설정 클래스입니다.
 * 이 클래스에서는 RestTemplate 빈을 생성하고 설정합니다.
 *
 * @author 김혁
 * @version 1.0
 * @since 2023-09-11
 */
@Configuration
public class RestConfig {
    /**
     * RestTemplate 빈을 생성하고 반환하는 메소드입니다.
     *
     * @return 새로 생성된 RestTemplate 객체를 반환합니다.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}