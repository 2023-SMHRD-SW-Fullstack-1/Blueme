package com.blueme.backend.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;


/*
작성자: 김혁
날짜(수정포함): 2023-09-11
설명: 날씨데이터 webClient Component (API 통신)
*/

@Component
public class WeatherAPIClient {

    @Value("${api-key.open-weather}")
    private String appid;

    private final WebClient webClient;

    public WeatherAPIClient() {
        this.webClient = WebClient.builder().baseUrl("https://api.openweathermap.org").build();
    }

    // 위도와 경도값을 받아 날씨 데이터 받는 로직
    public Mono<Map<String, Object>> getWeather(String lat, String lon) {
      return webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/data/2.5/weather")
                    .queryParam("lat", lat)
                    .queryParam("lng", lon)
                    .queryParam("appid", appid)
                    .build())
            .retrieve()
            .onStatus(HttpStatus::isError, clientResponse ->
                Mono.error(new RuntimeException("Weather API request failed with status: " + clientResponse.statusCode()))
            )
            .bodyToMono(new ParameterizedTypeReference<>() {});
    }

}

