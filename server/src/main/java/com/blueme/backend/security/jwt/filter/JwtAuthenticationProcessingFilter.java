package com.blueme.backend.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.jwt.service.JwtService;
import com.blueme.backend.utils.PasswordUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://172.30.1.13:3000")
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {
	
	private static final String NO_CHECK_URL = "/login"; // "/login" 경로에 대한 요청은 체크하지 않도록 설정
	
	private final JwtService jwtService;
	private final UsersJpaRepository usersJpaRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("JwtAuthenticationProcessingFilter - doFilterInternal start ");
		
		if(request.getRequestURI().equals(NO_CHECK_URL)) {
			log.info("/login 경로로 요청 들어옴");
			// "/login" 경로에 대한 요청이 들어온 경우,
			filterChain.doFilter(request, response);	// 다음 필터 호출
			return ;	// return으로 이후 로직이 실행되지 않도록 막기
		}
		
		// 사용자 요청 헤더에서 RefreshToken 추출
		String refreshToken = jwtService.extractRefreshToken(request)
				.filter(jwtService::isTokenValid)
				.orElse(null);
		
		if(refreshToken != null) {
			log.info("JwtAuthenticationProcessingFilter - doFilterInternal start - refresh != null ");
			checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
			return; 	// RefreshToken을 보냈을 경우에는 AccessToken을 재발급하고 인증 처리는 하지 않게 바로 return
		}else {
			log.info("JwtAuthenticationProcessingFilter - doFilterInternal start - refresh != null(else) ");
			checkAccessTokenAndAuthentication(request, response, filterChain); // AccessToken 검사 및 인증 작업 수행
		}
	}
		
		
		/**
	     *  리프레시 토큰으로 유저 정보 찾기 & 액세스 토큰/리프레시 토큰 재발급
	     */
	    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
	        usersJpaRepository.findByRefreshToken(refreshToken)
	                .ifPresent(user -> {
	                    String reIssuedRefreshToken = reIssueRefreshToken(user);
	                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(
	                    		user.getEmail()),reIssuedRefreshToken);
	                });
	
	}
	    /**
	     * 리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 
	     */
	    private String reIssueRefreshToken(Users user) {
	    	String reIssuedRefreshToken = jwtService.createRefreshToken();
	    	user.updateRefreshToken(reIssuedRefreshToken);
	    	usersJpaRepository.saveAndFlush(user);
	    	return reIssuedRefreshToken;
	    }
	    
	    /**
	     * 액세스 토큰 체크 & 인증 처리
	     */
	    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
	                                                  FilterChain filterChain) throws ServletException, IOException {
	        log.info("checkAccessTokenAndAuthentication() 호출");
	        
	        jwtService.extractAccessToken(request)
	                .filter(jwtService::isTokenValid)
	                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
	                        .ifPresent(email -> usersJpaRepository.findByEmail(email)
	                                .ifPresent(this::saveAuthentication)));
	        filterChain.doFilter(request, response);
	        log.info("checkAccessTokenAndAuthentication() 호출 끝");
	    }
	    
	    /**
	     * 인증 허가
	     */
	    public void saveAuthentication(Users myUser) {
	        String password = myUser.getPassword();
	        System.out.println("Password====>"+password);
	        if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
	            password = bCryptPasswordEncoder.encode(PasswordUtil.generateRandomPassword());
	        }

	        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
	                .username(myUser.getEmail())
	                .password(password)
	                .roles(myUser.getRole().name())
	                .build();

	        Authentication authentication =
	                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
	                authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	    }
	
	

}
