package com.blueme.backend.security.oauth2.handler;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.blueme.backend.dto.usersdto.UserInfoDTO;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.entity.Users.UserRole;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.jwt.service.JwtService;
import com.blueme.backend.security.oauth2.CustomOAuth2User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mysql.cj.Session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtService jwtService;
	private final UsersJpaRepository usersJpaRepository;

	@Value("${jwt.access.expiration}")
	private String accessTokenExpiration;
	
	String userId = null;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		log.info("OAuth2 Login 성공!");

		try {
			CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

			// 만약 소셜 첫로그인 후 (User의 Role이 GUEST일 경우) 처음 요청한 회원이므로 선호장르선택 페이지로 리다이렉트
			if (oAuth2User.getRole() == UserRole.GUEST) {
				String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
				response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
				response.addHeader("email", oAuth2User.getEmail());
				
                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
                Users findUser = usersJpaRepository.findByEmail(oAuth2User.getEmail())
                                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));
                userId = findUser.getId().toString();
                
                jwtService.sendAccessToken(response, userId.toString());
                response.addHeader("userId", userId.toString());
                
                UserInfoDTO userInfo = new UserInfoDTO(Long.parseLong(userId), oAuth2User.getEmail(), oAuth2User.getAttribute("name"), oAuth2User.getAttribute("img_url"));
		        
		        String userIdParam = "id="+userId;
		        String redirectUrl = "http://172.30.1.13:3000/SelectGenre?"+userIdParam;
                response.sendRedirect(redirectUrl);
                findUser.authorizeUser();
                usersJpaRepository.save(findUser);	// Role.USER 로 변경
                
			} else {
				
				log.info("oauth2user =========> {}", oAuth2User.toString());
				loginSuccess(response, oAuth2User, authentication); // 로그인에 성공한 경우 access, refresh 토큰 생성
				
			}
		} catch (Exception e) {
			log.info("onAuthenticationSuccess : {}", e.getMessage());
			throw e;
		}

	}

	// 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
	private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User, Authentication authentication)
			throws IOException {
		log.info("loginSuccess method start ...");

		usersJpaRepository.findByEmail(oAuth2User.getEmail()).ifPresent(user -> {
			String accessToken = jwtService.createAccessToken(user.getEmail());
			String refreshToken = jwtService.createRefreshToken();

			response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
			response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

			jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
			user.updateRefreshToken(refreshToken);
			usersJpaRepository.save(user);

			UserInfoDTO userInfo = new UserInfoDTO(user.getId(), user.getEmail(), user.getNickname(),
					user.getImg_url());

			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			
			String userInfoJson;
			try {
				userInfoJson = mapper.writeValueAsString(userInfo);
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(userInfoJson);
//				response.sendRedirect("http://172.30.1.13:3000/");
			} catch (Exception e) {
				log.info("error");
				e.printStackTrace();
			}

			log.info("소셜로그인 성공! 이메일 : {}", user.getEmail());
			log.info("소셜로그인 성공! AccessToken : {}", accessToken);
			log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
		});
	}
	

}
