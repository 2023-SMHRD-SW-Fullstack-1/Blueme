package com.blueme.backend.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.blueme.backend.model.entity.UserRole;
import com.blueme.backend.model.entity.Users;
import com.blueme.backend.security.oauth2.SocialType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *   Spring Security는 객체에 저장된 정보를 사용하여 UserPrincipal인증 및 권한을 부여
 */

//@Schema(hidden = true)
public class PrincipalDetails implements UserDetails, OAuth2User {
    private static final long serialVersionUID = 1L;
    private Users user;
    private Map<String, Object> attributes;

    // OAuth2.0 로그인시 사용
    public PrincipalDetails(Users user) {
        this.user = user;
    }

    public static PrincipalDetails create(Users user) {
        return new PrincipalDetails(user);
    }

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

    @Override
    public boolean isAccountNonExpired() {	// 계정 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {	// 계정 잠금 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {	// 자격 증명(비밀번호) 만료 여부 확인
        return true;
    }

    @Override
    public boolean isEnabled() {	// 계정 활성화 여부
        return true;
    }

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

    // 리소스 서버로 부터 받는 회원정보
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // User의 PrimaryKey
    @Override
    public String getName() {
        return user.getId() + "";
    }
}