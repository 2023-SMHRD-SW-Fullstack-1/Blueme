package com.blueme.backend.security.login.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import com.blueme.backend.dto.usersdto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * "/login" 요청 왔을 때 JSON 값을 매핑 처리하는 필터
 */
@Slf4j
public class CustomJsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final String DEFAULT_LOGIN_REQUEST_URL = "/login"; 
    private static final String HTTP_METHOD = "POST"; 
    private static final String CONTENT_TYPE = "application/json"; 
    private static final String USERNAME_KEY = "email"; 
    private static final String PASSWORD_KEY = "password"; 
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD); 

    private final ObjectMapper om;

    public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper om) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        this.om = om;
    }

    /**
     * 인증 시도 처리 
     */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException{
	if(request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)  ) {
        throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
    }

//    String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
//
//    Map<String, String> usernamePasswordMap = om.readValue(messageBody, Map.class);
//
//    String email = usernamePasswordMap.get(USERNAME_KEY);
//    String password = usernamePasswordMap.get(PASSWORD_KEY);
//
//    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
//    return this.getAuthenticationManager().authenticate(authRequest);
    
    ObjectMapper om = new ObjectMapper();
    LoginRequestDto loginDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
    
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
    		loginDto.getEmail(), 
    		loginDto.getPassword());
    
    return getAuthenticationManager().authenticate(authenticationToken);
    		
	}
    
    
}
