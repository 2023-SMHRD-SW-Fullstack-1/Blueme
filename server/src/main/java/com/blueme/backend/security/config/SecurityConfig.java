package com.blueme.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.jwt.filter.JwtAuthenticationProcessingFilter;
import com.blueme.backend.security.jwt.service.JwtService;
import com.blueme.backend.security.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import com.blueme.backend.security.login.handler.LoginFailureHandler;
import com.blueme.backend.security.login.handler.LoginSuccessHandler;
import com.blueme.backend.security.login.service.LoginService;
import com.blueme.backend.security.oauth2.handler.OAuth2LoginFailureHandler;
import com.blueme.backend.security.oauth2.handler.OAuth2LoginSuccessHandler;
import com.blueme.backend.security.oauth2.service.CustomOAuth2UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * Spring Security 설정 클래스
 * HTTP 보안, 인증 매니저, 로그인 성공/실패 핸들러 등의 Bean을 정의합니다.
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-27
 */

@CrossOrigin("*")
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig{	
	
	private final LoginService loginService;
	private final JwtService jwtService;
	private final UsersJpaRepository usersJpaRepository;
	private final ObjectMapper om;
	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
	private final CustomOAuth2UserService customOAuth2UserService;
	
	/**
	 * Spring Security Filter Chain 설정
	 * 
	 * @param http HttpSecurity 인스턴스
	 * @return 구성된 SecurityFilterChain 인스턴스
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http
				.cors().configurationSource(corsConfigurationSource())
				
				.and()
					.formLogin().disable()
					.httpBasic().disable()
					.csrf().disable()
					.headers().frameOptions().disable()
				.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					.authorizeRequests()
					.antMatchers(HttpMethod.OPTIONS).permitAll()
					.antMatchers("/admin/**").hasRole("ADMIN") 	// "ROLE_ADMIN"
					.antMatchers("/**").permitAll()
//				.antMatchers("/user/signup","signup", "deactivate","login","update","/index").permitAll() // "/signup" 회원가입페이지 접근 가능
//				.antMatchers("/login/oauth2/code/kakao/**", "/login/oauth2/code/google/**").permitAll()
					.anyRequest().authenticated() // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
                .and()
                	.oauth2Login()	// OAuth2 로그인 설정
                	.authorizationEndpoint().baseUri("/oauth2/authorization").and()
                	.redirectionEndpoint().baseUri("/login/oauth2/code/**").and()
                	.userInfoEndpoint().userService(customOAuth2UserService).and()
                	.successHandler(oAuth2LoginSuccessHandler)
                	.failureHandler(oAuth2LoginFailureHandler);

		http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
		http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);
               
		return http.build();
                
	}
	
	/**
	 * 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈을 생성합니다.
	 * 
	 * @return 새로운 BCryptPasswordEncoder 인스턴스
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * 사용자 인증을 위한 AuthenticationManager 빈을 생성합니다.
	 * 
	 * @return 새로운 ProviderManager 인스턴스 
	 */
	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();	// 비밀번호 검증
		provider.setPasswordEncoder(bCryptPasswordEncoder());
		provider.setUserDetailsService(loginService);
		return new ProviderManager(provider);
	}
	

	/**
	 * 로그인 성공시 처리를 담당하는 LoginSuccessHandler 빈을 생성합니다.
	 * 
	 * @return 새로운 LoginSuccessHandler 인스턴스 
	 */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, usersJpaRepository);
    }


    /**
	 * 로그인 실패시 처리를 담당하는 LoginFailureHandler 빈을 생성합니다.
	 * 
	 * @return 새로운 LoginFailureHandler 인스턴스 
	 */
    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    /**
     * CustomJsonUsernamePasswordAuthenticationFilter 빈을 생성합니다.
     * 이 필터는 JSON 형태의 사용자 이름과 비밀번호로 인증을 처리합니다.
     * 
     * @return 설정된 CustomJsonUsernamePasswordAuthenticationFilter 인스턴스 
     */
    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(om);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    /**
     * JwtAuthenticationProcessingFilter 빈을 생성합니다.
     * 이 필터는 JWT 토큰 기반의 인증을 처리합니다.
     * 
     * @return 설정된 JwtAuthenticationProcessingFilter 인스턴스
     */
    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationProcessingFilter(jwtService, usersJpaRepository);
        return jwtAuthenticationFilter;
    }
    
    /**
     * CORS 설정을 위한 CorsConfigurationSource 빈을 생성합니다
     * 
     * @return UrlBasedCorsConfigurationSource에 등록된 CorsConfiguration 객체
     */
    @Bean 
    public CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration configuration = new CorsConfiguration();
    	
    	configuration.addAllowedOriginPattern("*");	// 모든 도메인으로부터의 요청 허용
    	configuration.addAllowedHeader("*");		// 모든 HTTP 헤더를 허용
    	configuration.addAllowedMethod("*");		// 모든 HTTP 메소드를 허용
    	configuration.setAllowCredentials(true);
    	configuration.addExposedHeader("Authorization");
    	configuration.addExposedHeader("Authorization-Refresh");
    	
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", configuration);
    	return source;
    }
}
