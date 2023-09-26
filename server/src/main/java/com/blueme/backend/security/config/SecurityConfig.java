package com.blueme.backend.security.config;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blueme.backend.dto.usersdto.UserInfoDTO;
import com.blueme.backend.model.entity.FavCheckLists;
import com.blueme.backend.model.repository.FavArtistsJpaRepository;
import com.blueme.backend.model.repository.FavCheckListsJpaRepository;
import com.blueme.backend.model.repository.FavGenresJpaRepository;
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
	private final CorsFilter corsFilter;
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.cors().configurationSource(corsConfigurationSource()).and()
		.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
		
		
				.formLogin().disable()
				.httpBasic().disable()
				.csrf().disable()
				.headers().frameOptions().disable()
				.and()
				
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				
				.and()
				
				.authorizeRequests()
				// 소셜 로그인 완료되면 접근 주소 변경하깅
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN") 	// "ROLE_ADMIN"
				.antMatchers("/**").permitAll()
//				.antMatchers("/user/signup","signup", "deactivate","login","update","/index").permitAll() // "/signup" 회원가입페이지 접근 가능
//				.antMatchers("/login/oauth2/code/kakao/**", "/login/oauth2/code/google/**").permitAll()
                .anyRequest().authenticated() // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
                .and()
                // 소셜 로그인 설정
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorization").and()
                .redirectionEndpoint().baseUri("/login/oauth2/code/**").and()
                .userInfoEndpoint().userService(customOAuth2UserService).and()
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler);

		http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
		http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);
               
		return http.build();
                
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();	// 비밀번호 검증
		provider.setPasswordEncoder(bCryptPasswordEncoder());
		provider.setUserDetailsService(loginService);
		return new ProviderManager(provider);
	}
	

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, usersJpaRepository);
    }


    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    
    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(om);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationProcessingFilter(jwtService, usersJpaRepository);
        return jwtAuthenticationFilter;
    }
    
    @Bean 
    public CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration configuration = new CorsConfiguration();
    	
    	configuration.addAllowedOriginPattern("*");
    	configuration.addAllowedHeader("*");
    	configuration.addAllowedMethod("*");
    	configuration.setAllowCredentials(true);
    	configuration.addExposedHeader("Authorization");
    	configuration.addExposedHeader("Authorization-Refresh");
    	
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", configuration);
    	return source;
    }
 
	
}
