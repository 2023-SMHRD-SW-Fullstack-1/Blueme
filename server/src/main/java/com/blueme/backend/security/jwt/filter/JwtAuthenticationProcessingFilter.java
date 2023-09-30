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
import org.springframework.web.filter.OncePerRequestFilter;

import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.config.PrincipalDetails;
import com.blueme.backend.security.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 인증 필터 클래스
 * 이 필터는 모든 요청에 대해 한 번씩 실행되며 JWT 토큰의 유효성을 검사하고 인증 처리를 합니다.
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-26
 */

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {
	
	private static final String NO_CHECK_URL = "/login"; // "/login" 경로에 대한 요청은 체크하지 않도록 설정
	
	private final JwtService jwtService;
	private final UsersJpaRepository usersJpaRepository;
	
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
	
	/**
	 * HTTP 요청마다 한 번씩 실행되는 필터 메서드
	 * 
	 * @param request HTTP 요청 객체 
     * @param response HTTP 응답 객체 
     * @param filterChain 다음 필터를 호출하기 위한 FilterChain 객체
	 */
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
			checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
			return; 	// RefreshToken을 보냈을 경우에는 AccessToken을 재발급하고 인증 처리는 하지 않게 바로 return
		}else {
			checkAccessTokenAndAuthentication(request, response, filterChain); // AccessToken 검사 및 인증 작업 수행
		}
	}
		
		
		/**
	     * 리프레시 토큰으로 유저 정보 찾기 & 액세스 토큰/리프레시 토큰 재발급하는 메서드
	     * 
	     * @param response 클라이언트로 응답을 보내기 위한 HttpServletResponse 객체 
	     * @param refreshToken 클라이언트에서 보낸 리프레시 토큰 문자열 
	     */
	    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
	    	log.info("checkRefreshTokenAndReIssueAccessToken() 실행");
	        usersJpaRepository.findByRefreshToken(refreshToken)
	                .ifPresent(user -> {
	                    String reIssuedRefreshToken = reIssueRefreshToken(user);
	                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(
	                    		user.getEmail()),reIssuedRefreshToken);
	                });
	
	}
	    
	    /**
	     * 리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트하는 메서드
	     *
	     * @param user 리프레시 토큰을 업데이트할 사용자 정보가 담긴 Users 객체  
	     */
	    private String reIssueRefreshToken(Users user) {
	    	String reIssuedRefreshToken = jwtService.createRefreshToken();
	    	user.updateRefreshToken(reIssuedRefreshToken);
	    	usersJpaRepository.saveAndFlush(user);
	    	return reIssuedRefreshToken;
	    }
	    
	    /**
	     * 액세스 토큰 체크 & 인증 처리하는 메서드
	     * 
	     * @param request 클라이언트의 HttpServletRequest 요청객체 
	     * @param response 클라이언트로 응답을 보내기 위한 HttpServletResponse 객체
	     */
	    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
	                                                  FilterChain filterChain) throws ServletException, IOException {
	        jwtService.extractAccessToken(request)
	                .filter(jwtService::isTokenValid)
	                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
	                        .ifPresent(email -> usersJpaRepository.findByEmail(email)
	                                .ifPresent(this::saveAuthentication)));
	        filterChain.doFilter(request, response);
	    }
	    
	    /**
	     * 인증 정보를 SecurityContext에 저장하는 메서드
	     * @param myUser 인증할 사용자 정보가 담긴 Users 객체 
	     */
	    public void saveAuthentication(Users myUser) {
	        PrincipalDetails principalDetails = PrincipalDetails.create(myUser);
	        Authentication authentication =
	        new UsernamePasswordAuthenticationToken(principalDetails,null,authoritiesMapper.mapAuthorities(principalDetails.getAuthorities()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	    }
	
	

}