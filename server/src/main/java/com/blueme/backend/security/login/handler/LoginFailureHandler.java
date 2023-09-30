package com.blueme.backend.security.login.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * JWT 로그인 실패 시 처리하는 핸들러 클래스
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-26
 */

@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	/**
     * 인증 실패 시 호출되는 메서드
     *
     * @param request 클라이언트의 HttpServletRequest 요청객체 
     * @param response 클라이언트로 응답을 보내기 위한 HttpServletResponse 객체 
     * @param exception 인증 실패시 발생하는 AuthenticationException 객체
     */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write("로그인 실패! 이메일이나 비밀번호를 확인해주세요.");
		log.info("로그인 실패! 메시지 : {}", exception);

	
	}

	
}
