package com.blueme.backend.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.jwt.filter.JwtAuthenticationProcessingFilter;
import com.blueme.backend.security.jwt.service.JwtService;
import com.blueme.backend.security.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import com.blueme.backend.security.login.handler.LoginFailureHandler;
import com.blueme.backend.security.login.handler.LoginSuccessHandler;
import com.blueme.backend.security.login.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig{	
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UsersJpaRepository usersJpaRepository;
	@Autowired
	private ObjectMapper om;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
				.formLogin().disable()
				.httpBasic().disable()
				.csrf().disable()
				.headers().frameOptions().disable()
				.and()
				
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				
				.and()
				
				.authorizeRequests()
				
				.antMatchers("/", "/css/**","/image/**","/js/**","/favicon.ico","/h2-console/**","/user/**").permitAll()
				.antMatchers("signup", "deactivate","login").permitAll() // "/signup" 회원가입페이지 접근 가능
                .anyRequest().authenticated(); // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
//                .and()
                  // 소셜 로그인 설정
//                .oauth2Login()
		

		
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
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
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
	
}
