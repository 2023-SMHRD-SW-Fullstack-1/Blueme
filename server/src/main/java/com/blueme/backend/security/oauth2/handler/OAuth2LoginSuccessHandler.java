package com.blueme.backend.security.oauth2.handler;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.blueme.backend.dto.usersdto.UserInfoDTO;
import com.blueme.backend.enums.UserRole;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.jwt.service.JwtService;
import com.blueme.backend.security.oauth2.CustomOAuth2User;
import com.blueme.backend.security.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OAuth2 로그인 성공 시 동작을 정의하는 핸들러 클래스
 * JWT 토큰의 생성, 전송, 추출, 유효성 검사 등을 담당합니다.
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-27
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtService jwtService;
	private final UsersJpaRepository usersJpaRepository;
	private final HttpCookieOAuth2AuthorizationRequestRepository auth2AuthorizationRequestRepository;

	@Value("${jwt.access.expiration}")
	private String accessTokenExpiration;

	String userId = null;

	/**
	 * 인증(로그인)에 성공했을 때 호출되는 메서드
	 *
	 * @param request        HTTP 요청 객체
	 * @param response       HTTP 응답 객체
	 * @param authentication 인증 정보 객체
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		try {
			CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

			// 만약 소셜 첫로그인 후 (User의 Role이 GUEST일 경우) 처음 요청한 회원이므로 선호장르선택 페이지로 리다이렉트
			if (oAuth2User.getRole() == UserRole.GUEST) {
				String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
				response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
				response.addHeader("email", oAuth2User.getEmail());

				jwtService.sendAccessAndRefreshToken(response, accessToken, accessToken);
				Users findUser = usersJpaRepository.findByEmail(oAuth2User.getEmail())
						.orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));
				userId = findUser.getId().toString();

				jwtService.sendAccessToken(response, userId.toString());
				response.addHeader("userId", userId.toString());

				String userIdParam = "id=" + userId;
				String redirectUrl = "http://3.39.192.60:3000/SelectGenre?" + userIdParam;
				response.sendRedirect(redirectUrl);
				findUser.authorizeUser();
				usersJpaRepository.save(findUser); // Role.USER 로 변경

			} else {

				log.info("oauth2user : {}", oAuth2User.toString());
				loginSuccess(response, oAuth2User, authentication); // 로그인에 성공한 경우 access, refresh 토큰 생성

			}
		} catch (Exception e) {
			log.info("onAuthenticationSuccess : {}", e.getMessage());
			throw e;
		}

	}

	/**
	 * 소셜 로그인 성공 시 처리하는 메소드. access token과 refresh token을 생성하고 이를 응답 헤더에 추가한다.
	 *
	 * @param response       HTTP 응답 객체
	 * @param oAuth2User     소셜 로그인 사용자 정보가 담긴 CustomOAuth2User 객체
	 * @param authentication 인증 정보 객체
	 */
	private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User, Authentication authentication)
			throws IOException {
		log.info("loginSuccess method start ...");
		usersJpaRepository.findByEmail(oAuth2User.getEmail()).ifPresent(user -> {
			String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
			String refreshToken = jwtService.createRefreshToken();

			// response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
			// response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

			jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
			// jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
			System.out.println("accessToken : " + accessToken);

			UserInfoDTO userInfo = new UserInfoDTO(user.getId(), user.getEmail(), user.getNickname(), user.getImg_url(),
					user.getPlatformType(), user.getRole());

			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());

			String userInfoJson;
			try {
				userInfoJson = mapper.writeValueAsString(userInfo);
				log.info("userInfoJson : {}", userInfoJson);
				String encodedUserInfo = URLEncoder.encode(userInfoJson, "UTF-8");
				String redirectUri = UriComponentsBuilder.fromHttpUrl("http://3.39.192.60:3000/OauthInfo")
						.queryParam("OauthInfo", encodedUserInfo)
						.queryParam("accessToken", accessToken)
						.queryParam("refreshToken", refreshToken)
						.toUriString();

				response.sendRedirect(redirectUri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}