package com.blueme.backend.security.oauth2;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.blueme.backend.enums.UserRole;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.security.oauth2.userinfo.GoogleOauth2UserInfo;
import com.blueme.backend.security.oauth2.userinfo.KakaoOauth2UserInfo;
import com.blueme.backend.security.oauth2.userinfo.OAuth2UserInfo;
import com.blueme.backend.utils.PasswordUtil;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 각 소셜 로그인 서비스에서 받아오는 사용자 데이터를 처리하는 DTO 클래스.
 *  
 * @author 손지연
 * @version 1.0
 * @since 2023-09-27
 */
@Slf4j
@Getter
public class OAuthAttributes {

	private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
	private OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)
	
	/**
     * OAuthAttributes 생성자. nameAttributeKey와 oauth2UserInfo를 초기화합니다.
     *
     * @param nameAttributeKey 이름 속성 키 
     * @param oauth2UserInfo 사용자 정보를 담은 OAuth2UserInfo 객체 
     */
	@Builder
	public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
		this.nameAttributeKey = nameAttributeKey;
		this.oauth2UserInfo = oauth2UserInfo;
	}

	/**
	    * 주어진 소셜 플랫폼 타입과 사용자 정보에 따라 적절한 OAuthAttributes 객체를 반환하는 메서드
	    *
	    * @param platformType 소셜 플랫폼 타입  
	    * @param userNameAttributeName 이름 속성 키   
	    * @param attributes 사용자의 속성들을 담은 맵  
	    * @return 생성된 OAuthAttributes 객체   
	   */
	public static OAuthAttributes of(SocialType platformType, String userNameAttributeName,
			Map<String, Object> attributes) {
		
		log.info("OAuthAttributes method start!");

		if (platformType == SocialType.KAKAO) {
			return ofKakao(userNameAttributeName, attributes);
		}
		return ofGoogle(userNameAttributeName, attributes);
	}
	
	/**
	  * KakaoOauth2UserInfo를 이용하여 새로운 OAuthAttributes 객체를 생성하는 메서드
	  *
	  * @param userNameAttributeName 이름 속성 키   
	  * @param attributes 사용자의 속성들을 담은 맵  
	  * @return 생성된 KakaoOauth2User 기반의 OAuthAttributes 객체   
	 */
	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder().nameAttributeKey(userNameAttributeName)
				.oauth2UserInfo(new KakaoOauth2UserInfo(attributes)).build();
	}
	
	/**
	  * GoogleOauth2UserInfo 를 이용하여 새로운 OAuthAttrbutes 객체를 생성하는 메서드 
	  *
	  * @param userNameAttributeName 이름 속성 키   
	  * @param attributes 사용자의 속성들을 담은 맵  
	  * @return 생성된 GoogleOauth2User 기반의 OAuthAttributes 객체   
	 */
	public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder().nameAttributeKey(userNameAttributeName)
				.oauth2UserInfo(new GoogleOauth2UserInfo(attributes)).build();
	}


	/**
	 * OAuthAttributes 객체를 Users 엔티티로 변환하는 메서드
	 * 이메일이 없는 경우 새로운 UUID 기반의 이메일을 생성합니다.
	 *
	 * @param platformType 소셜 플랫폼 타입   
	 * @param oauth2UserInfo 사용자 정보를 담은 OAuth2UserInfo 객체 
	 * @return 생성된 Users 객체  
	 */
	public Users toEntity(SocialType platformType, OAuth2UserInfo oauth2UserInfo) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		log.info("Users toEntity (OAuthAttributes) start !");
		String email;
		if (oauth2UserInfo.getEmail() != null) {
		    email = oauth2UserInfo.getEmail();
		} else {
		    email = UUID.randomUUID() + "@kakao.com";
		}
		
			return Users.builder()
				.platformType(platformType.name())
				.socialId(oauth2UserInfo.getId())
				.img_url(oauth2UserInfo.getImageUrl())
				.email(email)
//				.email(oauth2UserInfo.getEmail())
//				.email(UUID.randomUUID() + "@socialUser.com")
				.nickname(oauth2UserInfo.getNickname())
				.role(UserRole.GUEST)
				.password(bCryptPasswordEncoder.encode(PasswordUtil.generateRandomPassword()))
				.build();
	}
}