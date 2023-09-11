package com.blueme.backend.security.oauth2.service;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.blueme.backend.security.oauth2.userinfo.OAuth2UserInfo;
import com.blueme.backend.utils.PasswordUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	private final UsersJpaRepository usersJpaRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		SocialType socialType = getPlatformType(registrationId);
		String userNameAttributeName = userRequest.getClientRegistration()
				.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();	// OAuth2 로그인 시 키(PK)가 되는 값
		Map<String, Object> attributes = oAuth2User.getAttributes();
		
		// socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
		OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);
		
		Users createdUser = getUser(extractAttributes, socialType);	// getUser()메소드로 User 객체 생성 후 반환
		log.info("email ====> {}",createdUser.getEmail());
		log.info("password ====> {}",createdUser.getPassword());
		log.info("platformType ====> {}",createdUser.getPlatformType());
		log.info("nickname ====> {}",createdUser.getNickname());
		
		// DefaultOAuth2User 를 구현한 CustomOAuth2User 객체를 생성해서 반환
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().name())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getRole()
        );
		
	}
	
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
    	log.info("saveUser start ....");
        Users createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
//        createdUser.setPassword(bCryptPasswordEncoder.encode(PasswordUtil.generateRandomPassword()));
        log.info("saveUser password : {}",createdUser.getPassword());
        log.info("saveUser email : {}",createdUser.getEmail());
        return usersJpaRepository.save(createdUser);
    }


}
