package com.blueme.backend.security.oauth2.util;

import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.SerializationUtils;

/**
 * 쿠키를 처리하는 유틸리티 클래스
 *  
 * @author 손지연
 * @version 1.0
 * @since 2023-09-25
 */
public class CookieUtils {
	
	/**
     * 주어진 이름의 쿠키를 반환하는 메서드
     *
     * @param request HttpServletRequest 객체
     * @param name 찾을 쿠키의 이름
     * @return Optional<Cookie> 객체. 해당 이름의 쿠키가 있으면 Optional에 담아 반환, 없으면 빈 Optional 반환
     */
	public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
	    Cookie[] cookies = request.getCookies();

	    if (cookies != null && cookies.length > 0) {
	      for (Cookie c : cookies) {
	        if (c.getName().equals(name)) {
	          return Optional.of(c);
	        }
	      }
	    }
	    return Optional.empty();
	  }

	/**
	 * 새로운 쿠키를 추가하는 메서드
	 *
	 * @param response HttpServletResponse 객체  
	 * @param name 추가할 쿠키의 이름  
	 * @param value 추가할 쿠키의 값  
	 * @param maxAge 추가할 쿠기의 최대 나이 (초 단위)
	 */
	  public static void addCookie(HttpServletResponse response, String name,
	      String value, int maxAge) {
	    Cookie cookie = new Cookie(name, value);
	    cookie.setPath("/");
	    cookie.setHttpOnly(true);
	    cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	  }
	  
	  /**
	   * 주어진 이름의 쿠기를 삭제하는 메서드
	   *
	   * @param request HttpServletRequest 객체   
	   * @param response HttpServletResponse 객체   
	   * @param name 삭제할 쿠기의 이름   
	  */
	  public static void deleteCookie(HttpServletRequest request,
	      HttpServletResponse response, String name) {
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null && cookies.length > 0) {
	      for (Cookie c : cookies) {
	        if (c.getName().equals(name)) {
	          c.setValue("");
	          c.setPath("/");
	          c.setMaxAge(0);
	          response.addCookie(c);
	        }
	      }
	    }
	  }
	  
	  /**
	   * Object를 직렬화하여 문자열로 변환하는 메서드
	   *
	   * @param o 직렬화 할 Object   
	   * @return 직렬화된 문자열  
	   */
	  public static String serialize(Object o) {
	    return Base64.getUrlEncoder()
	        .encodeToString(SerializationUtils.serialize(o));
	  }
	  
	  /**
	   * 주어진 Cookie 값을 역직렬화하여 특정 클래스 타입으로 변환하는 메서드
	   *
	   * @param cookie 역직렬화 할 Cookie   
	   * @return 역직렬화된 클래스 타입 인스턴스  
	   */
	  public static <T> T deserialize(Cookie cookie, Class<T> cls) {
	    return cls.cast(SerializationUtils.deserialize(
	        Base64.getUrlDecoder().decode(cookie.getValue())
	    ));
	  }
}
