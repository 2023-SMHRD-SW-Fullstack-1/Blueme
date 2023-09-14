package com.blueme.backend.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);	// 내 서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정
		config.addAllowedOrigin("*"); 	// 모든 ip에 응답을 허용
		config.addAllowedOrigin("http://172.30.1.13:3000");
		config.addAllowedHeader("**");	// 모든 header에 응답을 허용
		config.addAllowedMethod("**");	// 모든 post, get, put, delete, patch 요청을 허용

		source.registerCorsConfiguration("/api/**", config);
		return new CorsFilter(source);

	}
	
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		
//		// 1) Origin 설정
//        configuration.setAllowedOrigins(Arrays.asList("http://172.30.1.13:3000"));
//        
//        // 2) Methods 설정
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//        
//        // 3) Headers 설정
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        
//        return source;
//	}
}

