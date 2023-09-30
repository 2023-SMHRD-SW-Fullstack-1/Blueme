package com.blueme.backend.security.jwt.service;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.blueme.backend.model.repository.UsersJpaRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 서비스 클래스
 * JWT 토큰의 생성, 전송, 추출, 유효성 검사 등을 담당합니다.
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-26
 */

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    private final UsersJpaRepository usersJpaRepository;

    /**
     * AccessToken 생성 메서드
     * 
     * @param email 이메일 주소 
     * @return 생성된 AccessToken 문자열 
     */
    public String createAccessToken(String email) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) // 토큰 만료 시간 설정
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * RefreshToken 생성 메서드
     * 
     * 
     * @return 생성된 RefreshToken 문자열  
     */
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * AccessToken을 응답 헤더에 실어 보내는 메서드
     *
     * @param response 클라이언트로 응답을 보내기 위한 HttpServletResponse 객체 
     * @param accessToken 클라이언트에게 전송할 AccessToken 문자열  
     */
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    /**
    * AccessToken과 RefreshToken을 응답 헤더에 실어 보내는 메서드
    *
    * @param response 클라이언트로 응답을 보내기 위한 HttpServletResponse 객체 
    * @param accessToken 클라이언트에게 전송할 AccessToken 문자열  
    * @param refreshToken 클라이언트에게 전송할 RefreshToken 문자열  
    */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        // setRefreshTokenCookie(response, refreshToken);

        log.info("refreshToken : {}", refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    /**
     * HTTP 요청 헤더에서 RefreshToken을 추출하는 메서드
     *
     * @param request 클라이언트의 HttpServletRequest 요청객체 
     * @return 헤더에서 추출된 RefreshToken 문자열(Optional)
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * HTTP 요청 헤더에서 AccessToken을 추출하는 메서드
     *
     * @param request 클라이언트의 HttpServletRequest 요청객체  
     * @return 헤더에서 추출된 AccessToken 문자열(Optional)  
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * AccessToken에서 email 주소를 추출하는 메서드
     *
     * @param accessToken 이메일 주소를 포함하고 있는 AccessToken 문자열  
     * @return AccessToken에서 추출된 이메일 주소 문자열(Optional)  
     */
    public Optional<String> extractEmail(String accessToken) {
        try {
            log.info("extractEmail() 호출");
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(EMAIL_CLAIM)
                    .asString());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    /**
     * 응답에 AccessToken 헤더 설정하는 메서드
     * 
     * @param response 클라이언트로 응답을 보내기 위한 HttpServletResponse 객체 
     * @param accessToken 응답 헤더에 설정할 AccessToken 문자열   
     */
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    /**
     * 응답에 RefreshToken 헤더 설정하는 메서드
     * 
     * @param response 클라이언트로 응답을 보내기 위한 HttpServletResponse 객체 
     * @param refreshToken 응답 헤더에 설정할 RefreshToken 문자열 
     */
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }

    /**
     * 응답에 RefreshToken 쿠키를 설정하는 메서드
     *
     * @param response 클라이언트로 응답을 보내기 위한 HttpServletResponse 객체 
     * @param refreshToken 쿠키에 설정할 RefreshToken 문자열
     */
    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(refreshHeader, refreshToken);
        cookie.setMaxAge(refreshTokenExpirationPeriod.intValue());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * DB에 RefreshToken을 업데이트하는 메서드
     *
     * @param email RefreshToken을 업데이트할 사용자의 이메일 주소  
     * @param refreshToken DB에 업데이트할 RefreshToken 문자열  
     */
    public void updateRefreshToken(String email, String refreshToken) throws Exception {
        usersJpaRepository.findByEmail(email)
                .ifPresent(user -> user.updateRefreshToken(refreshToken));
    }

    /**
     * 토큰의 유효성을 검사하는 메서드
     *
     * @param token 유효성을 검사할 토큰 문자열  
     * @return 토큰의 유효성 결과 (유효하면 true, 그렇지 않으면 false)  
     */
    public boolean isTokenValid(String token) {
        try {
            log.info("토큰의 value 입니다. {}", token);
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰 {}", e.getMessage());
            return false;
        }
    }

}