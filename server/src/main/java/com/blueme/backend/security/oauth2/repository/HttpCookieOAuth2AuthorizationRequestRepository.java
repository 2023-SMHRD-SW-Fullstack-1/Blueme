package com.blueme.backend.security.oauth2.repository;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import com.blueme.backend.security.oauth2.util.CookieUtils;
import com.nimbusds.oauth2.sdk.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * OAuth2 인증 요청 쿠키 저장소
 */
@Component
@Slf4j
public class HttpCookieOAuth2AuthorizationRequestRepository
		implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

	public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE = "oauth2_auth_request";
	public static final String REDIRECT_URI_PARAM_COOKIE = "redirect_url";
	public final static String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
	public static final String USER_ROLE_PARAMETER = "role";
	private static final int COOKIE_EXPIRE_SECONDS = 180;

	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
		log.info("loadAuthorizationRequest() 실행");
		return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE)
				.map(c -> CookieUtils.deserialize(c, OAuth2AuthorizationRequest.class)).orElse(null);
	}

	@Override
	public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("saveAuthorizationRequest() 실행");
		if (authorizationRequest == null) {
			CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE);
			CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE);
			CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
			return;
		}

		CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE,
				CookieUtils.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
		String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE);
		if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
			CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE, redirectUriAfterLogin, COOKIE_EXPIRE_SECONDS);
		}

	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
		log.info("removeAuthorizationRequest() 실행");
		return this.loadAuthorizationRequest(request);
	}

	public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
		CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE);
		CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE);
		CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
	}
}
