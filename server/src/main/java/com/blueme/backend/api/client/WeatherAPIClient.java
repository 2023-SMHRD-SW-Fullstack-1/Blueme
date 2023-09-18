package com.blueme.backend.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.blueme.backend.model.vo.WeatherInfo;
import reactor.core.publisher.Mono;

/**
 * WeatherAPIClient는 OpenWeatherMap API와 상호작용하는 컴포넌트입니다.
 * <p>
 * 위도와 경도를 사용하여 실시간 날씨 데이터를 가져옵니다.
 * </p>
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-11
 */
@Component
public class WeatherAPIClient {
    @Value("${api-key.open-weather}")
    private String appid;

    private final WebClient webClient;

    /**
     * 생성자는 OpenWeatherMap API의 기본 URL로 WebClient를 설정합니다.
     */
    public WeatherAPIClient() {
        this.webClient = WebClient.builder().baseUrl("https://api.openweathermap.org").build();
    }

    /**
     * 제공된 위도와 경도에 기반한 실시간 날씨 데이터를 가져옵니다.
     *
     * @param lat 날씨 정보를 얻고자 하는 위치의 위도입니다.
     * @param lon 날씨 정보를 얻고자 하는 위치의 경도입니다.
     * @return OpenWeatherMap API에서 가져온 모든 데이터가 포함된 WeatherInfo 객체를 반환합니다.
     */
    public WeatherInfo getWeather(String lat, String lon) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/data/2.5/weather")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", appid)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse -> Mono.error(new RuntimeException(
                                "Weather API request failed with status: " + clientResponse.statusCode())))
                .bodyToMono(WeatherInfo.class)
                .block();
    }

}
