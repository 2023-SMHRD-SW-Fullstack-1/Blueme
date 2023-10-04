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
import com.blueme.backend.security.jwt.service.JwtService;
import com.blueme.backend.utils.ImageConverter;
import com.blueme.backend.utils.ImageToBase64;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 로그인 성공 시 처리하는 핸들러 클래스
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-26
 */

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtService jwtService;
	private final UsersJpaRepository usersJpaRepository;

	@Value("${jwt.access.expiration}")
	private String accessTokenExpiration;

	/**
     * 인증 성공 시 호출되는 메서드
     *
     * @param request 클라이언트의 HttpServletRequest 요청객체 
     * @param response 클라이언트로 응답을 보내기 위한 HttpServletResponse 객체 
     * @param authentication 인증에 성공한 Authentication 객체
     */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		String email = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
		// AccessToken과 RefreshToken 발급
		String accessToken = jwtService.createAccessToken(email);
		String refreshToken = jwtService.createRefreshToken();

		// 응답 헤더에 AccessToken과 RefreshToken 실어서 응답
		jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

		usersJpaRepository.findByEmail(email).ifPresent(user -> {
			
			if(user.getActiveStatus().equals("N")) {
				System.out.println("dsjfaeif");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}else {
			// DB의 유저 정보 업데이트
			user.updateRefreshToken(refreshToken);
			usersJpaRepository.saveAndFlush(user);

			String userInfoJson;
			try {

				UserInfoDTO userInfo = new UserInfoDTO(user.getId(), user.getEmail(), user.getNickname(),
						getBase64ImageForProfile(user.getImg_url()), user.getPlatformType(), user.getRole());

				System.out.println(userInfo.getImg_url());
				ObjectMapper mapper = new ObjectMapper();
				mapper.registerModule(new JavaTimeModule());
				userInfoJson = mapper.writeValueAsString(userInfo);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write(userInfoJson);
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.info("로그인 성공! 이메일 : {}", email);
			log.info("로그인 성공! AccessToken : {}", accessToken);
			log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
			}
		});
	}

	/**
	   * Authentication 객체에서 username(email)을 추출하는 메서드
	   *
	   * @param authentication 인증 정보가 담긴 Authentication 객체  
	   * @return 추출된 username(email) 문자열   
	 */
	public String extractUsername(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getUsername();
	}

	/**
	  * 프로필 이미지를 Base64 형태로 변환하는 메서드
	  *
	  * @param imgPath 프로필 이미지 파일의 경로  
	  * @return 변환된 Base64 문자열  
	 */
	public String getBase64ImageForProfile(String imgPath) {
		log.info("getBase64ImageForProfile method start!");
		if (imgPath != null) {
			try {
				Path filePath = Paths.get(imgPath);
				File file = filePath.toFile();
				ImageConverter<File, String> converter = new ImageToBase64();
				String base64 = null;
				base64 = converter.convert(file);
				return base64;
			} catch (IOException e) {
				log.info("error");
				log.info(e.getMessage());
			}
		}
		return null;
	}

}