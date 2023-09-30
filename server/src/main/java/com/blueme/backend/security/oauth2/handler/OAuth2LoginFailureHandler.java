package com.blueme.backend.security.oauth2.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * OAuth2 로그인 실패 시 동작을 정의하는 핸들러 클래스
 * AuthenticationFailureHandler 인터페이스를 구현하여 Spring Security에서 로그인 실패 시의 동작을 제어합니다.
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-14
 */

@Slf4j
@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {
	
	/**
	 * 인증(로그인)에 실패했을 때 호출되는 메서드
	 * 
	 * @param request HTTP 요청 객체 
     * @param response HTTP 응답 객체 
     * @param exception 인증 과정에서 발생한 예외 
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.getWriter().write("소셜 로그인 실패! 서버 로그를 확인해주세요.");
		log.info("소셜 로그인에 실패했습니다. 에러메시지 : {}", exception);
		log.info("소셜 로그인에 실패했습니다. 에러메시지 : {}", exception.getMessage());
		
	}
	

}
