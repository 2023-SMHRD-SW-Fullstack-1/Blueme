package com.blueme.backend.security.oauth2.service;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.UsersJpaRepository;
import com.blueme.backend.security.oauth2.CustomOAuth2User;
import com.blueme.backend.security.oauth2.OAuthAttributes;
import com.blueme.backend.security.oauth2.SocialType;
import com.blueme.backend.security.oauth2.dto.SessionUserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * OAuth2 로그인 시 사용자 정보를 로드하는 서비스 클래스
 * OAuth2UserService 인터페이스를 구현하여 Spring Security에서 OAuth2 로그인 시 사용자 정보를 어떻게 처리할지 제어합니다.
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-27
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	private final UsersJpaRepository usersJpaRepository;
	private final HttpSession httpSession;
	private static final String KAKAO = "kakao";
	
	/**
     * 주어진 사용자 요청에 따라 OAuth2User 객체를 로드(생성)하는 메서드
     *
     * @param userRequest 사용자 요청 객체 
     * @return 생성된 OAuth2User 객체 
     */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");
		
		/**
         * DefaultOAuth2UserService 객체를 생성하여, loadUser(userRequest)를 통해 DefaultOAuth2User 객체를 생성 후 반환
         */
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		/**
         * userRequest에서 registrationId 추출 후 registrationId으로 SocialType 저장
         */
		// 구글로그인인지 카카오로그인인지 서비스를 구분해주는 코드
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		SocialType socialType = getPlatformType(registrationId);
		// oauth2 로그인 진행 시 키가 되는 필드값(PK) 카카오 지원X
		String userNameAttributeName = userRequest.getClientRegistration()
				.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		
		// socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성 (SuccessHandler가 사용할 수 있도록 등록)
		OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);
		
		Users createdUser = getUser(extractAttributes, socialType);	// 로그인한 유저 정보
		SessionUserDto userDto = new SessionUserDto(createdUser);
		httpSession.setAttribute("user", userDto);
		
		
		// DefaultOAuth2User 를 구현한 CustomOAuth2User 객체를 생성해서 반환
        return new CustomOAuth2User(	// 로그인한 유저를 반환
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getRole()
        );
		
	}
	
	/**
	 * 주어진 속성과 플랫폼 타입에 따라 유저 정보를 찾거나 저장하는 메서드
	 *
	 * @param attributes 유저 속성  
	 * @param socialType 소셜 플랫폼 타입  
	 * @return 찾아진 또는 저장된 Users 객체  
	 */
	private Users getUser(OAuthAttributes attributes, SocialType socialType) {
        Users findUser = usersJpaRepository.findByPlatformTypeAndSocialId(socialType.name(),
                attributes.getOauth2UserInfo().getId()).orElse(null);

        if(findUser == null) {
            return saveUser(attributes, socialType);
        }
        return findUser;
    }
	
	/**
	 * 주어진 등록 아이디(registrationId)에 따라 플랫폼 타입을 반환하는 메서드
	 *
	 * @param registrationId 등록 아이디  
	 * @return 플랫폼 타입 (SocialType)
	 */
	private SocialType getPlatformType(String registrationId) {
        if(KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.GOOGLE;
    }
	
	
	/**
	 * 주어진 속성과 플랫폼 타입에 따라 새로운 유저를 저장하고 반환하는 메서드
	 *
	 * @param attributes 유저 속성   
	 * @param socialType 소셜 플랫폼 타입   
	 * @return 새로 저장된 Users 객체   
	 */
    private Users saveUser(OAuthAttributes attributes, SocialType socialType) {
        Users createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        System.out.println(createdUser.getEmail());
        System.out.println(createdUser.getNickname());
        return usersJpaRepository.save(createdUser);
    }


}
