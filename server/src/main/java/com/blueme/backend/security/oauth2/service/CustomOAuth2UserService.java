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

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	private final UsersJpaRepository usersJpaRepository;
	private final HttpSession httpSession;
	private static final String KAKAO = "kakao";
	
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
	 * 	PlatFormType과 attributes에 들어있는 소셜 로그인의 식별값 id를 통해 회원을 찾아 반환
	 *  만약 찾은 회원이 있다면, 그대로 반환하고 없다면 saveUser()를 호출하여 회원을 저장
	 */
	private Users getUser(OAuthAttributes attributes, SocialType socialType) {
        Users findUser = usersJpaRepository.findByPlatformTypeAndSocialId(socialType.name(),
                attributes.getOauth2UserInfo().getId()).orElse(null);

        if(findUser == null) {
            return saveUser(attributes, socialType);
        }
        return findUser;
    }
	
	
	private SocialType getPlatformType(String registrationId) {
        if(KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.GOOGLE;
    }
	
	
	/**
     * OAuthAttributes의 toEntity() 메소드를 통해 빌더로 User 객체 생성 후 반환
     * 생성된 User 객체를 DB에 저장 : socialType, socialId, email, role 값만 있는 상태
     */
    private Users saveUser(OAuthAttributes attributes, SocialType socialType) {
        Users createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        System.out.println(createdUser.getEmail());
        System.out.println(createdUser.getNickname());
        return usersJpaRepository.save(createdUser);
    }


}
