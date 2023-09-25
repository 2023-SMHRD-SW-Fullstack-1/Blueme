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
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.dto.TokenDto;
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
		
		String email = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
		        // AccessToken과 RefreshToken 발급
		        String accessToken = jwtService.createAccessToken(email);
		        String refreshToken = jwtService.createRefreshToken();
		        
		        // 응답 헤더에 AccessToken과 RefreshToken 실어서 응답
		        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

		        usersJpaRepository.findByEmail(email).ifPresent(user -> {
		        	
			        // DB의 유저 정보 업데이트
			        user.updateRefreshToken(refreshToken);
			        usersJpaRepository.saveAndFlush(user);
			        
//			        TokenDto tokenDto = TokenDto.builder()
//			        		.accessToken(accessToken)
//			        		.refreshToken(refreshToken)
//			        		.build();
//			        String tokenJson = new ObjectMapper().writeValueAsString(tokenDto);	// TokenDto 객체를 JSON 문자열로 변환
			        
			        UserInfoDTO userInfo = new UserInfoDTO(user.getId(), user.getEmail(), user.getNickname(), getBase64ImageForProfile(user.getImg_url()), user.getPlatformType(), user.getRole());
			        ObjectMapper mapper = new ObjectMapper();
			        mapper.registerModule(new JavaTimeModule());
		    
		        String userInfoJson;
		        try {
					userInfoJson = mapper.writeValueAsString(userInfo);
					response.setContentType("application/json;charset=UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write(userInfoJson);
//					response.getWriter().write(tokenJson);
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
	

	public String extractUsername(Authentication authentication) {
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
