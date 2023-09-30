package com.blueme.backend.security.login.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blueme.backend.model.entity.Users;
import com.blueme.backend.model.repository.UsersJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용자의 로그인 서비스를 관리하는 LoginService 클래스.
 * UserDetailsService 인터페이스를 구현하여 Spring Security에서 사용자 세부 정보를 제공합니다.
 * 
 * @author 손지연
 * @version 1.0
 * @since 2023-09-26
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {
	
	private final UsersJpaRepository usersJpaRepository;
	

	/**
     * 주어진 username(email)에 해당하는 UserDetails 객체를 반환하는 메소드.
     *
     * @param email 검색할 사용자의 이메일 주소 
     * @return 찾아낸 사용자 정보를 담은 UserDetails 객체 
     */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		log.info("loadUserByUsername start ...");
		Users user = usersJpaRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));
		
		// 찾아낸 사용자 정보로 UserDetails 객체 생성 후 반환
        // 여기서는 Spring Security의 User 클래스를 활용하여 UserDetails 객체를 생성
        // 비밀번호는 데이터베이스에 저장된 그대로(보통 암호화된 상태) 제공
        // 실제 비밀번호 확인(암호화 해제 등)은 Spring Security가 알아서 처리
		return User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRole().name())
				.build();
	}	

}
