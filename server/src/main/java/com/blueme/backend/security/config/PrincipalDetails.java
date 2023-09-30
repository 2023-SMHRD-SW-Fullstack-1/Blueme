package com.blueme.backend.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.blueme.backend.model.entity.Users;

/**
 * Spring Security에서 사용자의 인증 및 권한 부여를 위해 사용되는 PrincipalDetails 클래스
 * UserDetails와 OAuth2User 인터페이스를 구현하여 Spring Security가 인증을 처리하는 방식에 맞게 사용자 세부 정보를 제공합니다.
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-26
 */

//@Schema(hidden = true)
public class PrincipalDetails implements UserDetails, OAuth2User {
    
	private static final long serialVersionUID = 1L;
	private Users user;
    private Map<String, Object> attributes;

    /**
     * Users 객체를 받아서 PrincipalDetails 객체를 생성하는 생성자
     * 주로 OAuth2 로그인 시 사용됩니다.
     *
     * @param user Users 객체 
     */
    public PrincipalDetails(Users user) {
        this.user = user;
    }

    /**
     * 주어진 Users 객체로 PrincipalDetails 객체를 생성하고 반환하는 메서드
     *
     * @param user 사용자 정보가 담긴 Users 객체 
     * @return 생성된 PrincipalDetails 객체 
     */
    public static PrincipalDetails create(Users user) {
        return new PrincipalDetails(user);
    }

    /**
     * 사용자 정보를 반환하는 메소드.
     *
     * @return 이 클래스에 저장된 Users객체  
     */
    public Users getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getNickname();
    }

    /**
     * 계정 만료 여부 확인 메서드
     *
     * @return true - 계정이 만료되지 않았음을 의미  
     */	
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
    * 계정 잠금 여부 확인 메서드
    *
    * @return true - 계정이 잠기지 않았음을 의미  
    */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
    * 자격 증명(비밀번호) 만료 여부 확인 메소드. 
    *
    * @return true - 자격 증명이 만료되지 않았음을 의미   
    */		
    @Override
    public boolean isCredentialsNonExpired() {	
        return true;
    }

    /**
    * 계정 활성화 여부 확인 메서드
    *
    * @return true - 계정이 활성화 상태임을 의미   
    */
    @Override
    public boolean isEnabled() {	// 계정 활성화 여부
        return true;
    }

    /**
    * 사용자의 권한을 반환하는 메서드
    *
    * @return 사용자의 권한 정보를 담은 Collection 객체  
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
        collect.add(() -> {
            return "ROLE_" + user.getRole();
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return null;
    }

    /**
    * OAuth2 로그인 과정에서 리소스 서버로부터 받은 회원 정보를 반환하는 메서드
    *
    * @return 회원 정보를 담은 Map 객체  
    */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
    * User의 PrimaryKey 값을 문자열 형태로 반환하는 메서드
    *
    * @return User의 PrimaryKey 값  
    */
    @Override
    public String getName() {
        return user.getId() + "";
    }
}