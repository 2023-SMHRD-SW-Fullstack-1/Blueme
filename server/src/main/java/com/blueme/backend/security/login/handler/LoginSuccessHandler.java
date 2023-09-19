package com.blueme.backend.security.login.handler;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.blueme.backend.dto.usersdto.UserInfoDTO;
import com.blueme.backend.model.entity.Genres;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.jwt.service.JwtService;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 로그인 성공 시 처리하는 핸들러
 */

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtService jwtService;
	private final UsersJpaRepository usersJpaRepository;

	@Value("${jwt.access.expiration}")
	private String accessTokenExpiration;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		String email = extractUsername(authentication); 
		 usersJpaRepository.findByEmail(email).ifPresent(user -> {
		        // AccessToken과 RefreshToken 발급
		        String accessToken = jwtService.createAccessToken(email);
		        String refreshToken = jwtService.createRefreshToken();
		        
		        // 응답 헤더에 AccessToken과 RefreshToken 실어서 응답
		        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

		        // DB의 유저 정보 업데이트
		        user.updateRefreshToken(refreshToken);
		        usersJpaRepository.saveAndFlush(user);
		        
		        UserInfoDTO userInfo = new UserInfoDTO(user.getId(), user.getEmail(), user.getNickname(), getBase64ImageForProfile(user.getImg_url()));
		        
		        ObjectMapper mapper = new ObjectMapper();
		        mapper.registerModule(new JavaTimeModule());
		    
		        String userInfoJson;
		        try {
					userInfoJson = mapper.writeValueAsString(userInfo);
					response.setContentType("application/json;charset=UTF-8");
					response.getWriter().write(userInfoJson);
					log.info("user ::::: {}", userInfoJson);
				} catch (Exception e) {
					log.info("error");
					e.printStackTrace();
				}
		        
		        log.info("로그인 성공! 이메일 : {}", email);
		        log.info("로그인 성공! AccessToken : {}", accessToken);
		        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
		    });
	}
	

	private String extractUsername(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getUsername();
	}
	
	public String getBase64ImageForProfile(String imgPath) {
		log.info("getBase64ImageForProfile method start!");
		if (imgPath != null) {
			try {
				Path filePath = Paths.get(imgPath);
				File file = filePath.toFile();
				ImageConverter<File, String> converter = new ImageToBase64();
				String base64 = null;
				base64 = converter.convert(file);
				log.info(base64);
				return base64;
			} catch (IOException e) {
				log.info("error");
				log.info(e.getMessage());
			}
		}
		return null;
	}

}
