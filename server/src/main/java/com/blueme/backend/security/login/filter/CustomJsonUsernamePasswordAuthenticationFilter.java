package com.blueme.backend.security.login.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.blueme.backend.dto.usersdto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * "/login" 요청에 대해 JSON 값을 매핑 처리하는 필터 클래스
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-26
 */
public class CustomJsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final String DEFAULT_LOGIN_REQUEST_URL = "/login"; 
    private static final String HTTP_METHOD = "POST"; 
    private static final String CONTENT_TYPE = "application/json"; 
//    private static final String USERNAME_KEY = "email"; 
//    private static final String PASSWORD_KEY = "password"; 
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD); 

    private final ObjectMapper om;

    public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper om) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        this.om = om;
    }

    /**
     * 인증 시도를 처리하는 메서드
     *
     * @param request 클라이언트의 HttpServletRequest 요청객체 
     * @param response 클라이언트로 응답을 보내기 위한 HttpServletResponse 객체 
     * @return 인증된 Authentication 객체
     */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException{
	if(request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)  ) {
        throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
    }
    
    ObjectMapper om = new ObjectMapper();
    LoginRequestDto loginDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
    
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
    		loginDto.getEmail(), 
    		loginDto.getPassword());
    
    return getAuthenticationManager().authenticate(authenticationToken);
    		
	}
    
    
}