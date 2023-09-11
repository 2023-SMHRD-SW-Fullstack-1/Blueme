package com.blueme.backend.security.oauth2.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.blueme.backend.model.entity.Users.UserRole;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.jwt.service.JwtService;
import com.blueme.backend.security.oauth2.CustomOAuth2User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private final JwtService jwtService;
	private final UsersJpaRepository usersJpaRepository;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("OAuth2 Login 성공!");
		
		try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // 만약 소셜 첫로그인 후 닉네임 값을 받는다면 (User의 Role이 GUEST일 경우-보류) 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
//            if(oAuth2User.getRole() == UserRole.ADMIN) {
//                String accessToken = jwtService.createAccessToken(,oAuth2User.getEmail());
//                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
//                response.sendRedirect("oauth2/sign-up"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트
//
//                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
//                User findUser = userRepository.findByEmail(oAuth2User.getEmail())
//                                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));
//                findUser.authorizeUser();
//            } else {
            System.out.println("oauth2user=====>"+ oAuth2User.toString());
                loginSuccess(response, oAuth2User, authentication); // 로그인에 성공한 경우 access, refresh 토큰 생성
//            }
        } catch (Exception e) {
            throw e;
        }

    
				
		
	}
	
	// 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User, Authentication authentication) throws IOException {
    	String email = extractUsername(authentication); 
    	
		 usersJpaRepository.findByEmail(email).ifPresent(user -> {
		        Long id = user.getId();
		        String nickname = user.getNickname();
		        String platformType = user.getPlatformType();
		        String accessToken = jwtService.createAccessToken(id,oAuth2User.getEmail(),nickname,platformType);
		        String refreshToken = jwtService.createRefreshToken();
		        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
		        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);
		        
		        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
		        try {
					jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
				} catch (Exception e) {
					log.info("loginSuccess error ...");
					e.printStackTrace();
				}
		 });

		 }
	private String extractUsername(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getUsername();
	}
}
