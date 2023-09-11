package com.blueme.backend.security.login.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 로그인 성공 시 처리하는 핸들러
 */

@Slf4j
@RequiredArgsConstructor
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
		        Long id = user.getId();
		        String nickname = user.getNickname();
		        String platformType = user.getPlatformType();

		        // AccessToken과 RefreshToken 발급
		        String accessToken = jwtService.createAccessToken(id, email, nickname, platformType);
		        String refreshToken = jwtService.createRefreshToken();

		        // 응답 헤더에 AccessToken과 RefreshToken 실어서 응답
		        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

		        // DB의 유저 정보 업데이트
		        user.updateRefreshToken(refreshToken);
		        usersJpaRepository.saveAndFlush(user);
		        
		        log.info("로그인 성공! 이메일 : {}", email);
		        log.info("로그인 성공! AccessToken : {}", accessToken);
		        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
		    });
	}
	

	private String extractUsername(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getUsername();
	}

}
